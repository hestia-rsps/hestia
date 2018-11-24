package world.gregs.hestia.game.systems.movement

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import world.gregs.hestia.game.component.map.Position
import world.gregs.hestia.game.component.movement.*
import world.gregs.hestia.game.component.update.UpdateMoveType
import world.gregs.hestia.services.Aspect
import world.gregs.hestia.game.update.DirectionUtils.Companion.DELTA_X
import world.gregs.hestia.game.update.DirectionUtils.Companion.DELTA_Y

/**
 * RunSystem
 * Processes entity run steps
 * Entity must have [RunToggled] toggled
 */
class RunSystem : IteratingSystem(Aspect.all(Position::class, Mobile::class, Steps::class, Walk::class, RunToggled::class)) {
    private lateinit var stepsMapper: ComponentMapper<Steps>
    private lateinit var runMapper: ComponentMapper<Run>
    private lateinit var shiftPositionMapper: ComponentMapper<ShiftPosition>
    private lateinit var walkingMapper: ComponentMapper<Walking>
    private lateinit var runningMapper: ComponentMapper<Running>
    private lateinit var updateMoveTypeMapper: ComponentMapper<UpdateMoveType>

    override fun process(entityId: Int) {
        val steps = stepsMapper.get(entityId)

        if(steps.hasNext) {
            //Add run step with location
            val run = runMapper.create(entityId)
            run.direction = steps.nextDirection
            //Shift entities location
            val shift = shiftPositionMapper.create(entityId)
            shift.add(DELTA_X[run.direction], DELTA_Y[run.direction])
            if(!runningMapper.has(entityId)) {
                //Remove walking flag
                walkingMapper.remove(entityId)
                //Flag running movement type
                runningMapper.create(entityId)
                //Flag
                updateMoveTypeMapper.create(entityId)
            }
        } else {
            if(!walkingMapper.has(entityId)) {
                //Remove Running flag
                runningMapper.remove(entityId)
                //Flag movement type as walking
                walkingMapper.create(entityId)
                //Flag
                updateMoveTypeMapper.create(entityId)
            }
        }
    }

}