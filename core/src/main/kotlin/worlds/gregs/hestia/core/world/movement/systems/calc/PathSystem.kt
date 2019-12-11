package worlds.gregs.hestia.core.world.movement.systems.calc

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import worlds.gregs.hestia.core.world.movement.components.calc.Path

/**
 * PathSystem
 * Calculates the steps required for an entity to reach a position
 */
@Wire(failOnNull = false, injectInherited = true)
class PathSystem : BaseMovementSystem(Path::class) {

    private lateinit var pathMapper: ComponentMapper<Path>
    private lateinit var pathFinder: PathFinderSystem

    override fun process(entityId: Int) {
        //Request to walk
        val nav = pathMapper.get(entityId)

        //Queue steps
        val steps = pathFinder.findRoute(entityId, nav.strategy, nav.alternative, nav.collide)
        for (i in steps - 1 downTo 0) {
            if (!addWalkSteps(entityId, pathFinder.lastPathBufferX[i], pathFinder.lastPathBufferY[i], 25, false)) {
                break
            }
        }
        //Remove request
        pathMapper.remove(entityId)
    }
}