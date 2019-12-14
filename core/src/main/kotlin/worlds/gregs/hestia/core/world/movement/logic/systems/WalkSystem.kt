package worlds.gregs.hestia.core.world.movement.logic.systems

import com.artemis.ComponentMapper
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.world.movement.api.Mobile
import worlds.gregs.hestia.core.world.movement.model.components.Shift
import worlds.gregs.hestia.core.world.movement.api.types.Walk
import worlds.gregs.hestia.core.world.movement.model.components.Steps
import worlds.gregs.hestia.core.world.movement.model.components.types.Walking
import worlds.gregs.hestia.core.display.update.model.components.RunStep
import worlds.gregs.hestia.core.display.update.model.components.WalkStep
import worlds.gregs.hestia.artemis.Aspect

/**
 * WalkSystem
 * Processes entity walk steps
 */
class WalkSystem : Walk(Aspect.all(Position::class, Mobile::class, Steps::class)) {
    private lateinit var stepsMapper: ComponentMapper<Steps>

    private lateinit var walkingMapper: ComponentMapper<Walking>
    private lateinit var walkMapper: ComponentMapper<WalkStep>
    private lateinit var runMapper: ComponentMapper<RunStep>
    private lateinit var shiftMapper: ComponentMapper<Shift>
    override fun process(entityId: Int) {
        val steps = stepsMapper.get(entityId)

        walkMapper.remove(entityId)
        runMapper.remove(entityId)

        if (steps.hasNext) {
            //Add a walk step with direction
            val walk = walkMapper.create(entityId)
            walk.direction = steps.nextDirection
            //Shift entities location
            val shift = shiftMapper.create(entityId)
            shift.add(walk.direction.deltaX, walk.direction.deltaY)
        } else {
            stepsMapper.remove(entityId)
        }

    }

    override fun isWalking(entityId: Int): Boolean {
        return walkingMapper.has(entityId)
    }

    override fun hasStep(entityId: Int): Boolean {
        return walkMapper.has(entityId)
    }
}
