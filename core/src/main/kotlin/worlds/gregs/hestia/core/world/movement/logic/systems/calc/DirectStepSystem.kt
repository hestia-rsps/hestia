package worlds.gregs.hestia.core.world.movement.logic.systems.calc

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import worlds.gregs.hestia.core.world.movement.model.components.calc.Step

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