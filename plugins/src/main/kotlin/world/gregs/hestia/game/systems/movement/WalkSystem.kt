package world.gregs.hestia.game.systems.movement

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import world.gregs.hestia.game.component.map.Position
import world.gregs.hestia.game.component.movement.*
import world.gregs.hestia.services.Aspect
import world.gregs.hestia.game.update.DirectionUtils.Companion.DELTA_X
import world.gregs.hestia.game.update.DirectionUtils.Companion.DELTA_Y

/**
 * WalkSystem
 * Processes entity walk steps
 */
class WalkSystem : IteratingSystem(Aspect.all(Position::class, Mobile::class, Steps::class)) {
    private lateinit var positionMapper: ComponentMapper<Position>
    //    private lateinit var mobileMapper: ComponentMapper<Mobile>
    private lateinit var stepsMapper: ComponentMapper<Steps>
    private lateinit var walkMapper: ComponentMapper<Walk>
    private lateinit var runMapper: ComponentMapper<Run>
    private lateinit var shiftPositionMapper: ComponentMapper<ShiftPosition>

    override fun process(entityId: Int) {
        val position = positionMapper.get(entityId)
//        val mobile = mobileMapper.get(entityId)
        val steps = stepsMapper.get(entityId)

        walkMapper.remove(entityId)
        runMapper.remove(entityId)

//        mobile.lastPosition = Position.clone(position)//TODO is this needed if move/tele then run or is it covered by [MobileSystem]?

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