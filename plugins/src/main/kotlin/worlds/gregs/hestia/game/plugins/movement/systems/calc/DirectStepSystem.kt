package worlds.gregs.hestia.game.plugins.movement.systems.calc

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import worlds.gregs.hestia.game.plugins.movement.components.calc.Step

/**
 * DirectStepSystem
 * Calculates the direct steps required for an entity to reach a position
 */
@Wire(failOnNull = false, injectInherited = true)
class DirectStepSystem : BaseMovementSystem(Step::class) {
    private lateinit var stepMapper: ComponentMapper<Step>

    override fun process(entityId: Int) {
        //Request to walk
        val navigate = stepMapper.get(entityId)
        //Add steps
        addWalkSteps(entityId, navigate.x, navigate.y, navigate.max, navigate.check)
        //Remove request
        stepMapper.remove(entityId)
    }
}