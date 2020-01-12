package worlds.gregs.hestia.core.task.logic.systems

import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.TaskContinuation
import worlds.gregs.hestia.core.task.model.await.Ticks
import worlds.gregs.hestia.core.task.model.components.TaskQueue
import worlds.gregs.hestia.core.task.model.events.ProcessTaskSuspension

/**
 *  Processes [TaskContinuation] with [Ticks]
 *  Decreases each tick until equal to zero then [ProcessTaskSuspension] is dispatched
 */
class TicksSystem : IteratingSystem(Aspect.all(TaskQueue::class)) {

    private lateinit var tasks: Tasks

    override fun process(entityId: Int) {
        val suspension = tasks.getSuspension(entityId)
        if(suspension is Ticks) {
            suspension.ticks--
            if(suspension.ticks <= 0) {
                tasks.resume(entityId, suspension, Unit)
            }
        }
    }

}