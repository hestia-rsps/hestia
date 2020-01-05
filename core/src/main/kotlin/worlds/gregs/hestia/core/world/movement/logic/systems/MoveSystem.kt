package worlds.gregs.hestia.core.world.movement.logic.systems

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.player.model.events.FlagMoveType
import worlds.gregs.hestia.core.world.movement.api.types.Move
import worlds.gregs.hestia.core.world.movement.model.MovementType
import worlds.gregs.hestia.core.world.movement.model.components.Shift
import worlds.gregs.hestia.core.world.movement.model.components.types.MoveStep
import worlds.gregs.hestia.core.world.movement.model.components.types.Movement

/**
 * MoveSystem
 * Instantly moves player to [Position]
 * Aka teleporting without delay & animations
 */
class MoveSystem : Move(Aspect.all(Position::class, MoveStep::class)) {
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var moveMapper: ComponentMapper<MoveStep>
    private lateinit var shiftMapper: ComponentMapper<Shift>
    private lateinit var movementMapper: ComponentMapper<Movement>
    private lateinit var es: EventSystem

    override fun process(entityId: Int) {
        val position = positionMapper.get(entityId)
        val move = moveMapper.get(entityId)
        val movementType = movementMapper.get(entityId)

        //Move to location
        shiftMapper.create(entityId).add(move.x - position.x, move.y - position.y, move.plane - position.plane)

        //Remove command
        moveMapper.remove(entityId)

        //Flag movement type as "move"
        movementType.set(MovementType.Move)
        es.dispatch(FlagMoveType(entityId))
    }

    override fun hasStep(entityId: Int): Boolean {
        return moveMapper.has(entityId)
    }
}