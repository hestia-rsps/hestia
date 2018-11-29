package worlds.gregs.hestia.game.plugins.movement.systems

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.game.plugins.core.components.map.Position
import worlds.gregs.hestia.game.plugins.movement.components.Mobile
import worlds.gregs.hestia.game.plugins.movement.components.Shift
import worlds.gregs.hestia.game.plugins.movement.components.Steps
import worlds.gregs.hestia.game.plugins.movement.components.types.Run
import worlds.gregs.hestia.game.plugins.movement.components.types.Walk
import worlds.gregs.hestia.game.update.DirectionUtils.Companion.DELTA_X
import worlds.gregs.hestia.game.update.DirectionUtils.Companion.DELTA_Y
import worlds.gregs.hestia.services.Aspect

/**
 * WalkSystem
 * Processes entity walk steps
 */
class WalkSystem : IteratingSystem(Aspect.all(Position::class, Mobile::class, Steps::class)) {
    private lateinit var stepsMapper: ComponentMapper<Steps>
    private lateinit var walkMapper: ComponentMapper<Walk>
    private lateinit var runMapper: ComponentMapper<Run>
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
            shift.add(DELTA_X[walk.direction], DELTA_Y[walk.direction])
        } else {
            stepsMapper.remove(entityId)
        }

    }
}