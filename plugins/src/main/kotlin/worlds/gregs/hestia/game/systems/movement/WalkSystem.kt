package worlds.gregs.hestia.game.systems.movement

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.game.component.map.Position
import worlds.gregs.hestia.game.component.movement.*
import worlds.gregs.hestia.services.Aspect
import worlds.gregs.hestia.game.update.DirectionUtils.Companion.DELTA_X
import worlds.gregs.hestia.game.update.DirectionUtils.Companion.DELTA_Y

/**
 * WalkSystem
 * Processes entity walk steps
 */
class WalkSystem : IteratingSystem(Aspect.all(Position::class, Mobile::class, Steps::class)) {
    private lateinit var stepsMapper: ComponentMapper<Steps>
    private lateinit var walkMapper: ComponentMapper<Walk>
    private lateinit var runMapper: ComponentMapper<Run>
    private lateinit var shiftPositionMapper: ComponentMapper<ShiftPosition>

    override fun process(entityId: Int) {
        val steps = stepsMapper.get(entityId)

        walkMapper.remove(entityId)
        runMapper.remove(entityId)

        if (steps.hasNext) {
            //Add a walk step with direction
            val walk = walkMapper.create(entityId)
            walk.direction = steps.nextDirection
            //Shift entities location
            val shift = shiftPositionMapper.create(entityId)
            shift.add(DELTA_X[walk.direction], DELTA_Y[walk.direction])
        } else {
            stepsMapper.remove(entityId)
        }

    }
}