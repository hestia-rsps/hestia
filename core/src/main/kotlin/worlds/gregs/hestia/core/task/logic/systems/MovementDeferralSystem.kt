package worlds.gregs.hestia.core.task.logic.systems

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import kotlinx.coroutines.runBlocking
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.components.TaskQueue
import worlds.gregs.hestia.core.task.model.components.getDeferral
import worlds.gregs.hestia.core.world.movement.api.Mobile
import worlds.gregs.hestia.core.world.movement.model.components.Shift
import worlds.gregs.hestia.game.task.DeferralType
import worlds.gregs.hestia.game.task.TaskScope
import worlds.gregs.hestia.artemis.Aspect

data class MovementDeferral(val position: Position) : DeferralType

suspend fun TaskScope.walkedTo(position: Position) {
    deferral = MovementDeferral(position)
    defer()
}

class MovementDeferralSystem : IteratingSystem(Aspect.all(TaskQueue::class, Mobile::class, Position::class, Shift::class)) {

    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var taskQueueMapper: ComponentMapper<TaskQueue>
    private lateinit var taskQueue: Tasks

    override fun process(entityId: Int) = runBlocking {
        val deferral = taskQueueMapper.getDeferral(entityId)
        if(deferral is MovementDeferral) {
            val position = positionMapper.get(entityId)
            if(deferral.position.same(position)) {
                taskQueue.resume(entityId)
            }
        }
    }
}