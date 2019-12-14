package worlds.gregs.hestia.core.task.logic.systems

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.components.TaskQueue
import worlds.gregs.hestia.core.task.model.events.ProcessDeferral
import worlds.gregs.hestia.game.task.DeferralType
import worlds.gregs.hestia.game.task.DeferringCoroutine
import worlds.gregs.hestia.game.task.TaskScope
import worlds.gregs.hestia.artemis.Aspect

data class TickDeferral(var ticks: Int) : DeferralType

suspend fun TaskScope.wait(ticks: Int) {
    deferral = TickDeferral(ticks)
    defer()
}

/**
 *  Processes [DeferringCoroutine] with [TickDeferral]
 *  Decreases each tick until equal to zero then [ProcessDeferral] is dispatched
 */
class TickDeferralSystem : IteratingSystem(Aspect.all(TaskQueue::class)) {

    private lateinit var taskQueueMapper: ComponentMapper<TaskQueue>
    private lateinit var taskQueue: Tasks

    override fun process(entityId: Int) {
        val queue = taskQueueMapper.get(entityId)
        val coroutine = queue.peek() ?: return
        val deferral = coroutine.deferral
        if(deferral is TickDeferral) {
            deferral.ticks--
            if (deferral.ticks <= 0) {
                this.taskQueue.resume(entityId)
            }
        }
    }

}