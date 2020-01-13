package worlds.gregs.hestia.core.task.logic.systems

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import net.mostlyoriginal.api.event.common.Subscribe
import worlds.gregs.hestia.core.action.model.Action
import worlds.gregs.hestia.core.task.api.Task
import worlds.gregs.hestia.core.task.api.TaskCancellation
import worlds.gregs.hestia.core.task.api.TaskType
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.InactiveTask
import worlds.gregs.hestia.core.task.model.TaskContinuation
import worlds.gregs.hestia.core.task.model.TaskDeque
import worlds.gregs.hestia.core.task.model.await.Resendable
import worlds.gregs.hestia.core.task.model.components.TaskQueue
import worlds.gregs.hestia.core.task.model.events.ProcessTaskSuspension
import worlds.gregs.hestia.core.task.model.events.StartTask
import kotlin.coroutines.*

class TaskSystem : Tasks() {

    private lateinit var taskQueueMapper: ComponentMapper<TaskQueue>
    private lateinit var es: EventSystem

    override fun process(entityId: Int) {
        val taskQueue = taskQueueMapper.get(entityId)
        while(taskQueue.inactiveTasks.isNotEmpty()) {
            val (priority, block) = taskQueue.inactiveTasks.poll()
            //Create task
            val task = TaskContinuation(EmptyCoroutineContext)
            //Start task
            val cont = block.createCoroutine(task, task)
            processTask(entityId, task, cont, Unit)
            //Queue
            if(!task.isCancelled && !task.isCompleted) {
                val queue = taskQueue.active.getOrPut(priority) { TaskDeque() }
                queue.push(task)
            }
        }
    }

    override fun getSuspension(entityId: Int): TaskType<*>? {
        val component = taskQueueMapper.get(entityId) ?: return null
        val queue = purge(component.active) ?: return null
        val peek = queue.peek()
        resend(queue, peek?.suspension)
        return peek?.suspension
    }

    override fun <T> resume(entityId: Int, type: TaskType<T>, result: T): Boolean {
        val taskQueue = taskQueueMapper.get(entityId) ?: return false
        val queue = purge(taskQueue.active) ?: return false
        val task = queue.peek()
        val suspension = task?.suspension
        resend(queue, suspension)
        return if (suspension != null) {
            processTask(entityId, task, type.continuation, result)
            true
        } else {
            false
        }
    }

    /**
     * If [deque] was recently polled then resend the most up to date [suspension] if it's an [Action]
     */
    internal fun resend(deque: TaskDeque, suspension: TaskType<*>?) {
        if(deque.needsUpdate) {
            deque.needsUpdate = false
            if(suspension is Resendable) {
                (suspension as? Action)?.perform(suspension)
            }
        }
    }

    /**
     *  Returns the [TaskDeque] of the top most active task
     *  Removes empty [TaskDeque] and completed or cancelled [Task]'s in the process
     */
    internal fun purge(map: MutableMap<Int, TaskDeque>?): TaskDeque? {
        val iterator = map?.iterator() ?: return null
        while(iterator.hasNext()) {
            val queue = iterator.next().value
            var peek = queue.peek()
            while(queue.isNotEmpty()) {
                if (peek.isCompleted || peek.isCancelled) {//Purge
                    queue.poll()
                    peek = queue.peek()
                } else {//Valid value
                    return queue
                }
            }
            iterator.remove()
        }
        return null
    }

    internal fun <T> processTask(entityId: Int, task: Task, continuation: Continuation<T>, result: T) {
        continuation.resume(result)
        if(task.isActive) {
            es.dispatch(ProcessTaskSuspension(entityId, task.suspension ?: return))
        }
    }

    override fun cancel(entityId: Int, cause: TaskCancellation) {
        val component = taskQueueMapper.get(entityId)
        val poll = purge(component?.active)?.poll() ?: return
        poll.resumeWithException(cause)
    }

    override fun cancelAll(entityId: Int, cause: TaskCancellation, priority: Int) {
        val component = taskQueueMapper.get(entityId) ?: return
        component.active.forEach { (p, queue) ->
            if(priority == -1 || p <= priority) {
                while(queue.isNotEmpty()) {
                    queue.poll().resumeWithException(cause)
                }
            }
        }
    }

    @Subscribe
    private fun start(event: StartTask) {
        activateTask(event.entity, event.task)
    }

    override fun activateTask(entityId: Int, task: InactiveTask) {
        val component = taskQueueMapper.get(entityId) ?: return
        component.inactiveTasks.addLast(task)
    }
}