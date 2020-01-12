package worlds.gregs.hestia.core.task.logic.systems

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import net.mostlyoriginal.api.event.common.Subscribe
import worlds.gregs.hestia.core.task.api.Task
import worlds.gregs.hestia.core.task.api.TaskCancellation
import worlds.gregs.hestia.core.task.api.TaskType
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.InactiveTask
import worlds.gregs.hestia.core.task.model.TaskContinuation
import worlds.gregs.hestia.core.task.model.components.TaskQueue
import worlds.gregs.hestia.core.task.model.context.EntityContext
import worlds.gregs.hestia.core.task.model.context.ParamContext
import worlds.gregs.hestia.core.task.model.events.ProcessTaskSuspension
import worlds.gregs.hestia.core.task.model.events.StartTask
import java.util.*
import kotlin.coroutines.Continuation
import kotlin.coroutines.createCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class TaskSystem : Tasks() {

    private lateinit var taskQueueMapper: ComponentMapper<TaskQueue>
    private lateinit var es: EventSystem

    override fun inserted(entityId: Int) {
        val taskQueue = taskQueueMapper.get(entityId)
        taskQueue.context = EntityContext(world, entityId)
    }

    override fun process(entityId: Int) {
        val taskQueue = taskQueueMapper.get(entityId)
        var inactiveTask = taskQueue.inactiveTasks.poll()

        while(inactiveTask != null) {
            val (block, param) = inactiveTask
            //Create task
            val task = TaskContinuation(if(param != null) ParamContext(taskQueue.context, param) else taskQueue.context)
            //Start task
            val cont = block.createCoroutine(task, task)
            processTask(entityId, taskQueue, task, cont, Unit)
            //Queue
            if(!task.isCancelled && !task.isCompleted) {
                taskQueue.active.push(task)
            }
            //Queue next
            inactiveTask = taskQueue.inactiveTasks.poll()
        }
    }

    override fun getSuspension(entityId: Int): TaskType<*>? {
        val component = taskQueueMapper.get(entityId) ?: return null
        component.active.purgeHead()
        val peek = component.active.peek()
        return peek?.suspension
    }

    override fun <T> resume(entityId: Int, type: TaskType<T>, result: T): Boolean {
        val taskQueue = taskQueueMapper.get(entityId) ?: return false
        taskQueue.active.purgeHead()
        val task = taskQueue.active.peek()
        val suspension = task?.suspension
        return if (suspension != null) {
            processTask(entityId, taskQueue, task, type.continuation, result)
            true
        } else {
            false
        }
    }

    internal fun Deque<Task>.purgeHead() {
        val task = peek() ?: return
        if(task.isCompleted || task.isCancelled) {
            poll()
            purgeHead()
        }
    }

    internal fun check(entityId: Int, queue: TaskQueue, task: Task) {
        when {
            task.suspension == null || task.isCompleted || task.isCancelled -> queue.active.remove(task)
            task.isActive -> es.dispatch(ProcessTaskSuspension(entityId, task.suspension ?: return))
        }
    }

    internal fun <T> processTask(entityId: Int, queue: TaskQueue, task: Task, continuation: Continuation<T>, result: T) {
        continuation.resume(result)
        check(entityId, queue, task)
    }

    override fun cancel(entityId: Int, cause: TaskCancellation) {
        val component = taskQueueMapper.get(entityId)
        val poll = component?.active?.poll() ?: return
        poll.resumeWithException(cause)
    }

    override fun cancelAll(entityId: Int, cause: TaskCancellation) {
        val component = taskQueueMapper.get(entityId)
        while (component?.active?.size ?: -1 > 0) {
            val poll = component?.active?.poll() ?: return
            poll.resumeWithException(cause)
        }
    }

    @Subscribe
    private fun start(event: StartTask) {
        activateTask(event.entity, event.task)
    }

    override fun activateTask(entityId: Int, task: InactiveTask<*>) {
        val component = taskQueueMapper.get(entityId) ?: return
        component.inactiveTasks.push(task)
    }
}