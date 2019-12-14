package worlds.gregs.hestia.core.task.logic.systems

import com.artemis.systems.IteratingSystem
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.core.task.api.Task
import worlds.gregs.hestia.core.task.api.TaskType
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.TaskContinuation
import worlds.gregs.hestia.core.task.model.components.TaskQueue
import worlds.gregs.hestia.core.task.model.events.ProcessTaskSuspension

data class TickSuspension(var ticks: Int, override val continuation: CancellableContinuation<Unit>) : TaskType<Unit>

suspend fun Task.wait(ticks: Int) = suspendCancellableCoroutine<Unit> {
    suspension = TickSuspension(ticks, it)
}

/**
 *  Processes [TaskContinuation] with [TickSuspension]
 *  Decreases each tick until equal to zero then [ProcessTaskSuspension] is dispatched
 */
class TickSuspensionSystem : IteratingSystem(Aspect.all(TaskQueue::class)) {

    private lateinit var tasks: Tasks

    override fun process(entityId: Int) {
        val suspension = tasks.getSuspension(entityId)
        if(suspension is TickSuspension) {
            suspension.ticks--
            if(suspension.ticks <= 0) {
                tasks.resume(entityId, suspension, Unit)
            }
        }
    }

}