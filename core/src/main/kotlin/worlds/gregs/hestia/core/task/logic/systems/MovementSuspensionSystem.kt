package worlds.gregs.hestia.core.task.logic.systems

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.task.api.Task
import worlds.gregs.hestia.core.task.api.TaskType
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.components.TaskQueue
import worlds.gregs.hestia.core.world.movement.api.Mobile
import worlds.gregs.hestia.core.world.movement.model.components.Steps

data class Route(val steps: Int, val alternative: Boolean)

class RouteSuspension(override val continuation: CancellableContinuation<Route>) : TaskType<Route> {
    lateinit var route: Route
}

suspend fun Task.awaitRoute() = suspendCancellableCoroutine<Route> {
    suspension = RouteSuspension(it)
}

class MovementSuspensionSystem : IteratingSystem(Aspect.all(TaskQueue::class, Mobile::class, Position::class)) {

    private lateinit var stepsMapper: ComponentMapper<Steps>
    private lateinit var tasks: Tasks

    override fun process(entityId: Int) {
        val suspension = tasks.getSuspension(entityId)
        if(suspension is RouteSuspension) {
            val steps = stepsMapper.get(entityId)
            //Resume once step queue is empty
            if (steps == null || !steps.hasNext) {
                tasks.resume(entityId, suspension, suspension.route)
            }
        }
    }

}