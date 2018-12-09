package worlds.gregs.hestia.game.plugins.movement.systems.calc

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import worlds.gregs.hestia.game.plugins.movement.components.calc.Navigate

/**
 * Navigation system
 * Calculates the steps required for an entity to reach a position
 */
@Wire(failOnNull = false, injectInherited = true)
class NavigationSystem : BaseMovementSystem(Navigate::class) {
    private lateinit var navigateMapper: ComponentMapper<Navigate>

    override fun process(entityId: Int) {
        //Request to walk
        val navigate = navigateMapper.get(entityId)
        //Add steps
        addWalkSteps(entityId, navigate.x, navigate.y, navigate.max, navigate.check)
        //Remove request
        navigateMapper.remove(entityId)
    }
}