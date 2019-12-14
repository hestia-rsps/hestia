package worlds.gregs.hestia.core.task.logic.systems

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import net.mostlyoriginal.api.event.common.Subscribe
import worlds.gregs.hestia.core.task.api.*
import worlds.gregs.hestia.core.task.model.InactiveTask
import worlds.gregs.hestia.core.task.model.TaskContinuation
import worlds.gregs.hestia.core.task.model.components.TaskQueue
import worlds.gregs.hestia.core.task.model.context.EntityContext
import worlds.gregs.hestia.core.task.model.context.ParamContext
import worlds.gregs.hestia.core.task.model.events.ProcessTaskSuspension
import worlds.gregs.hestia.core.task.model.events.StartTask
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
        while (taskQueue.inactiveTasks.size > 0) {
            val inactiveTask = taskQueue.inactiveTasks.poll()
            val (inactive, param) = inactiveTask
            val (priority, block) = inactive
            //Cancel if strong task
            if (priority == TaskPriority.High) {
                cancelAll(entityId, TaskCancellation.Priority)
            }
            //Create coroutine
            val task = TaskContinuation(if(param != null) ParamContext(taskQueue.context, param) else taskQueue.context)
            //Queue
            taskQueue.active.push(task)
            //Start
            val cont = block.createCoroutine(task, task)
            processTask(entityId, taskQueue, task, cont, Unit)
        }
    }

    override fun getSuspension(entityId: Int): TaskType<*>? {
        val component = taskQueueMapper.get(entityId) ?: return null
        val peek = component.active.peek()
        return peek?.suspension
    }

    override fun <T> resume(entityId: Int, type: TaskType<T>, result: T): Boolean {
        val taskQueue = taskQueueMapper.get(entityId) ?: return false
        val task = taskQueue.active.peek()
        val suspension = task?.suspension
        return if (suspension != null) {
            processTask(entityId, taskQueue, task, type.continuation, result)
            true
        } else {
            false
        }
    }

    internal fun check(entityId: Int, queue: TaskQueue, task: Task) {
        when {
            task.isCompleted || task.isCancelled -> queue.active.remove(task)
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
        val (entityId, task) = event
        activateTask(entityId, task)
    }

    override fun activateTask(entityId: Int, task: InactiveTask<*>) {
        val component = taskQueueMapper.get(entityId) ?: return
        component.inactiveTasks.push(task)
    }
}