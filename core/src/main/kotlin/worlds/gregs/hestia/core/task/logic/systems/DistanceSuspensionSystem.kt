package worlds.gregs.hestia.core.task.logic.systems

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.artemis.invoke
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.task.api.Task
import worlds.gregs.hestia.core.task.api.TaskType
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.components.TaskQueue
import worlds.gregs.hestia.core.world.movement.api.Mobile

data class DistanceSuspension(val targetId: Int, val distance: Int, override val continuation: CancellableContinuation<Boolean>) : TaskType<Boolean>

suspend fun Task.awaitDistance(targetId: Int, distance: Int) = suspendCancellableCoroutine<Boolean> {
    suspension = DistanceSuspension(targetId, distance, it)
}

class DistanceSuspensionSystem : IteratingSystem(Aspect.all(TaskQueue::class, Mobile::class, Position::class)) {

    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var tasks: Tasks

    override fun process(entityId: Int) {
        val suspension = tasks.getSuspension(entityId)
        if(suspension is DistanceSuspension) {
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