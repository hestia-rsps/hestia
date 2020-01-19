package worlds.gregs.hestia.core.task.logic.systems

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.await.Route
import worlds.gregs.hestia.core.task.model.components.TaskQueue
import worlds.gregs.hestia.core.world.movement.api.Mobile
import worlds.gregs.hestia.core.world.movement.model.components.Steps

class RouteSystem : IteratingSystem(Aspect.all(TaskQueue::class, Mobile::class, Position::class)) {

    private lateinit var stepsMapper: ComponentMapper<Steps>
    private lateinit var tasks: Tasks

    override fun process(entityId: Int) {
        val suspension = tasks.getSuspension(entityId)
        if(suspension is Route) {
            val steps = stepsMapper.get(entityId)
            //Resume once step queue is empty
            if (steps == null || !steps.hasNext) {
                tasks.resume(entityId, suspension, suspension.route)
            }
        }
    }

}