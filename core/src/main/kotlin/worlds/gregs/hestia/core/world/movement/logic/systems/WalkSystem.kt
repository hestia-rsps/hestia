package worlds.gregs.hestia.core.world.movement.logic.systems

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.core.display.update.model.components.RunStep
import worlds.gregs.hestia.core.display.update.model.components.WalkStep
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.player.model.events.FlagMoveType
import worlds.gregs.hestia.core.world.movement.api.Mobile
import worlds.gregs.hestia.core.world.movement.api.types.Walk
import worlds.gregs.hestia.core.world.movement.model.MovementType
import worlds.gregs.hestia.core.world.movement.model.components.Shift
import worlds.gregs.hestia.core.world.movement.model.components.Steps
import worlds.gregs.hestia.core.world.movement.model.components.types.Movement

/**
 * WalkSystem
 * Processes entity walk steps
 */
class WalkSystem : Walk(Aspect.all(Position::class, Mobile::class, Steps::class)) {
    private lateinit var stepsMapper: ComponentMapper<Steps>

    private lateinit var walkMapper: ComponentMapper<WalkStep>
    private lateinit var runMapper: ComponentMapper<RunStep>
    private lateinit var shiftMapper: ComponentMapper<Shift>
    private lateinit var movementMapper: ComponentMapper<Movement>
    private lateinit var es: EventSystem

    override fun process(entityId: Int) {
        val steps = stepsMapper.get(entityId)
        val movementType = movementMapper.get(entityId)

        walkMapper.remove(entityId)
        runMapper.remove(entityId)

        if (steps.hasNext) {
            step(entityId)
            movementType.set(MovementType.Walk)
            es.dispatch(FlagMoveType(entityId))
        } else {
            if(movementType.actual == MovementType.Walk || movementType.actual == MovementType.Run) {
                movementType.set(MovementType.None)
                es.dispatch(FlagMoveType(entityId))
                stepsMapper.get(entityId)?.clear()
            }
        }
    }

    fun step(entityId: Int) {
        val steps = stepsMapper.get(entityId)
        //Add a walk step with direction
        val walk = walkMapper.create(entityId)
        walk.direction = steps.nextDirection
        //Shift entities location
        val shift = shiftMapper.create(entityId)
        shift.add(walk.direction.deltaX, walk.direction.deltaY)
    }

    override fun hasStep(entityId: Int): Boolean {
        return walkMapper.has(entityId)
    }
}
