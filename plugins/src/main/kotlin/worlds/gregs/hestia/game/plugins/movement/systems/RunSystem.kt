package worlds.gregs.hestia.game.plugins.movement.systems

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.api.core.components.Position
import worlds.gregs.hestia.api.movement.components.Shift
import worlds.gregs.hestia.api.movement.types.Run
import worlds.gregs.hestia.game.events.FlagMoveType
import worlds.gregs.hestia.game.plugins.movement.components.Mobile
import worlds.gregs.hestia.game.plugins.movement.components.RunToggled
import worlds.gregs.hestia.game.plugins.movement.components.Steps
import worlds.gregs.hestia.game.plugins.movement.components.types.RunStep
import worlds.gregs.hestia.game.plugins.movement.components.types.Running
import worlds.gregs.hestia.game.plugins.movement.components.types.WalkStep
import worlds.gregs.hestia.game.plugins.movement.components.types.Walking
import worlds.gregs.hestia.services.Aspect

/**
 * RunSystem
 * Processes entity run steps
 * Entity must have [RunToggled] toggled
 */
class RunSystem : Run(Aspect.all(Position::class, Mobile::class, Steps::class, WalkStep::class, RunToggled::class)) {
    private lateinit var stepsMapper: ComponentMapper<Steps>
    private lateinit var runMapper: ComponentMapper<RunStep>
    private lateinit var shiftMapper: ComponentMapper<Shift>
    private lateinit var walkingMapper: ComponentMapper<Walking>
    private lateinit var runningMapper: ComponentMapper<Running>
    private lateinit var runToggledMapper: ComponentMapper<RunToggled>
    private lateinit var es: EventSystem

    override fun process(entityId: Int) {
        val steps = stepsMapper.get(entityId)

        if(steps.hasNext) {
            //Add run step with location
            val run = runMapper.create(entityId)
            run.direction = steps.nextDirection
            //Shift entities location
            val shift = shiftMapper.create(entityId)
            shift.add(run.direction.deltaX, run.direction.deltaY)
            if(!runningMapper.has(entityId)) {
                //Remove walking flag
                walkingMapper.remove(entityId)
                //Flag running movement type
                runningMapper.create(entityId)
                //Flag
                es.dispatch(FlagMoveType(entityId))
            }
        } else {
            if(!walkingMapper.has(entityId)) {
                //Remove Running flag
                runningMapper.remove(entityId)
                //Flag movement type as walking
                walkingMapper.create(entityId)
                //Flag
                es.dispatch(FlagMoveType(entityId))
            }
        }
    }

    override fun isRunning(entityId: Int): Boolean {
        return runToggledMapper.has(entityId)
    }

    override fun hasStep(entityId: Int): Boolean {
        return runMapper.has(entityId)
    }
}