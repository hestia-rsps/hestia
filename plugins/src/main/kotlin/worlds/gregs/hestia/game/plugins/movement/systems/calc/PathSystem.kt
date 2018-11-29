package worlds.gregs.hestia.game.plugins.movement.systems.calc

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.path.RouteStrategy
import worlds.gregs.hestia.game.plugins.core.components.entity.Size
import worlds.gregs.hestia.game.plugins.core.components.map.Position
import worlds.gregs.hestia.game.plugins.movement.components.Steps
import worlds.gregs.hestia.game.plugins.movement.components.calc.Path
import worlds.gregs.hestia.game.plugins.region.systems.RegionMapSystem
import worlds.gregs.hestia.game.plugins.region.systems.RegionSystem

/**
 * Navigation system
 * Calculates the steps required for an entity to reach a position
 */
class PathSystem : BaseMovementSystem(Path::class) {

    private lateinit var pathMapper: ComponentMapper<Path>
    private lateinit var regionSystem: RegionSystem
    private lateinit var routeFinder: RouteFinderSystem
    private lateinit var rms: RegionMapSystem
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var sizeMapper: ComponentMapper<Size>
    private lateinit var stepsMapper: ComponentMapper<Steps>

    override fun process(entityId: Int) {
        //Request to walk
        val path = pathMapper.get(entityId)
        //TODO temp remove - handle packets as systems?
        stepsMapper.remove(entityId)
        //Queue steps
        val steps = find(entityId, path.strategy)
        for (i in steps - 1 downTo 0) {
            if (!addWalkSteps(entityId, routeFinder.lastPathBufferX[i], routeFinder.lastPathBufferY[i], path.max, path.check)) {
                break
            }
        }
        //Remove request
        pathMapper.remove(entityId)
    }

    private fun find(entityId: Int, strategy: RouteStrategy, findAlternative: Boolean = true): Int {
        val position = positionMapper.get(entityId)
        val sizeX = if(sizeMapper.has(entityId)) sizeMapper.get(entityId).sizeX else 1
        val sizeY = if(sizeMapper.has(entityId)) sizeMapper.get(entityId).sizeY else 1
        return routeFinder.findRoute(position.x, position.y, position.plane, sizeX, sizeY, strategy, findAlternative, regionSystem, rms)
    }
}