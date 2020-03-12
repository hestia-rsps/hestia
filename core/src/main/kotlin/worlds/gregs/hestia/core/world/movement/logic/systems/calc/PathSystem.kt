package worlds.gregs.hestia.core.world.movement.logic.systems.calc

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.await.Route
import worlds.gregs.hestia.core.task.model.await.RouteResult
import worlds.gregs.hestia.core.world.movement.model.components.calc.Path

/**
 * PathSystem
 * Calculates the steps required for an entity to reach a position
 */
@Wire(failOnNull = false, injectInherited = true)
class PathSystem : BaseMovementSystem(Path::class) {

    private lateinit var pathMapper: ComponentMapper<Path>
    private lateinit var pathFinder: PathFinderSystem
    private lateinit var tasks: Tasks

    override fun process(entityId: Int) {
        //Request to walk
        val nav = pathMapper.get(entityId)

        //Queue steps
        val steps = pathFinder.findRoute(entityId, nav.strategy, nav.partial, nav.collide)
        (tasks.getSuspension(entityId) as? Route)?.route = RouteResult(steps, pathFinder.lastIsPartial())//TODO temp
        for (i in steps - 1 downTo 0) {
            if (!addWalkSteps(entityId, pathFinder.lastPathBufferX[i], pathFinder.lastPathBufferY[i], 25, false)) {
                break
            }
        }
        //Remove request
        pathMapper.remove(entityId)
    }
}