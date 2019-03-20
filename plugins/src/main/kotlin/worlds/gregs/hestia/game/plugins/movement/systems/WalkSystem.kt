package worlds.gregs.hestia.game.plugins.movement.systems

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.entity.Position
import worlds.gregs.hestia.api.movement.Mobile
import worlds.gregs.hestia.api.movement.components.Shift
import worlds.gregs.hestia.api.movement.types.Walk
import worlds.gregs.hestia.game.plugins.movement.components.Steps
import worlds.gregs.hestia.game.plugins.movement.components.types.Walking
import worlds.gregs.hestia.game.update.components.RunStep
import worlds.gregs.hestia.game.update.components.WalkStep
import worlds.gregs.hestia.services.Aspect

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
