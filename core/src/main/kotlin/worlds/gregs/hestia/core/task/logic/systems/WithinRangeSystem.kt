package worlds.gregs.hestia.core.task.logic.systems

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.artemis.invoke
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.await.WithinRange
import worlds.gregs.hestia.core.task.model.components.TaskQueue
import worlds.gregs.hestia.core.world.movement.api.Mobile

class WithinRangeSystem : IteratingSystem(Aspect.all(TaskQueue::class, Mobile::class, Position::class)) {

    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var tasks: Tasks

    override fun process(entityId: Int) {
        val suspension = tasks.getSuspension(entityId)
        if(suspension is WithinRange) {
            val (targetId, distance) = suspension

            val targetPosition = positionMapper(targetId)
            val position = positionMapper(entityId)

            if(!world.entityManager.isActive(entityId) || targetPosition == null || position == null) {
                tasks.resume(entityId, suspension, false)
                return
            }

            if(position.withinDistance(targetPosition, distance)) {
                tasks.resume(entityId, suspension, true)
            }
        }
    }

}