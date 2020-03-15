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
        load(entityId)
        //Add steps
        addSteps(entityId, navigate.x, navigate.y, navigate.max)
        //Remove request
        stepMapper.remove(entityId)
    }

    override fun addWalkStep(entityId: Int, nextX: Int, nextY: Int): Boolean {
        val navigate = stepMapper.get(entityId)
        return addWalkStep(entityId, nextX, nextY, navigate.check)
    }
}