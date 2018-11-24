package worlds.gregs.hestia.game.systems.movement

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.game.component.map.Position
import worlds.gregs.hestia.game.component.movement.Mobile
import worlds.gregs.hestia.game.component.movement.Navigate
import worlds.gregs.hestia.game.component.movement.Steps
import worlds.gregs.hestia.services.Aspect

/**
 * Navigation system
 * Calculates the steps required for an entity to reach a position
 */
class PathSystem : IteratingSystem(Aspect.all(Position::class, Mobile::class)) {
    private lateinit var navigateMapper: ComponentMapper<Navigate>

    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var stepsMapper: ComponentMapper<Steps>

    override fun process(entityId: Int) {
        //Request to walk
        val navigate = navigateMapper.get(entityId)
        //Add steps

        //Remove request
        navigateMapper.remove(entityId)
    }

    /*fun find(entity: Entity, strategy: RouteStrategy, findAlternative: Boolean = true): Path {
        val steps = WalkRouteFinder.findRoute(entity.x, entity.y, entity.plane, entity.size, strategy, findAlternative, entity.regions())
        return Path(steps, WalkRouteFinder.lastPathBufferX, WalkRouteFinder.lastPathBufferY, WalkRouteFinder.lastIsAlternative())
    }*/
}