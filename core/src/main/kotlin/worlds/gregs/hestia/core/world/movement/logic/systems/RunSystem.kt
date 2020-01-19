package worlds.gregs.hestia.core.world.movement.logic.systems

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.core.display.update.model.components.RunStep
import worlds.gregs.hestia.core.display.update.model.components.WalkStep
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.player.model.events.FlagMoveType
import worlds.gregs.hestia.core.world.movement.api.Mobile
import worlds.gregs.hestia.core.world.movement.api.types.Run
import worlds.gregs.hestia.core.world.movement.model.MovementType
import worlds.gregs.hestia.core.world.movement.model.components.RunToggled
import worlds.gregs.hestia.core.world.movement.model.components.Shift
import worlds.gregs.hestia.core.world.movement.model.components.Steps
import worlds.gregs.hestia.core.world.movement.model.components.types.Movement

/**
 * RunSystem
 * Processes entity run steps
 * Entity must have [RunToggled] toggled
 */
class RunSystem : Run(Aspect.all(Position::class, Mobile::class, Steps::class, WalkStep::class, RunToggled::class)) {
    private lateinit var stepsMapper: ComponentMapper<Steps>
    private lateinit var runMapper: ComponentMapper<RunStep>
    private lateinit var shiftMapper: ComponentMapper<Shift>
    private lateinit var runToggledMapper: ComponentMapper<RunToggled>
    private lateinit var movementMapper: ComponentMapper<Movement>
    private lateinit var es: EventSystem

    override fun process(entityId: Int) {
        val steps = stepsMapper.get(entityId)
        val movementType = movementMapper.get(entityId)

        if(steps.hasNext) {
            step(entityId, steps)
            if(movementType.visual != MovementType.Run) {
                movementType.set(MovementType.Run)
                es.dispatch(FlagMoveType(entityId))
            }
        } else {
            movementType.set(MovementType.Run, MovementType.Walk)
            es.dispatch(FlagMoveType(entityId))
        }
    }

    private fun step(entityId: Int, steps: Steps) {
        //Add run step with location
        val run = runMapper.create(entityId)
        run.direction = steps.nextDirection
        //Shift entities location
        val shift = shiftMapper.create(entityId)
        shift.add(run.direction.deltaX, run.direction.deltaY)
    }

    override fun isRunning(entityId: Int): Boolean {
        return runToggledMapper.has(entityId)
    }

    override fun hasStep(entityId: Int): Boolean {
        return runMapper.has(entityId)
    }
}