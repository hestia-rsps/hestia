package worlds.gregs.hestia.game.plugins.movement.systems

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.game.api.movement.Move
import worlds.gregs.hestia.game.api.movement.Shift
import worlds.gregs.hestia.game.events.FlagMoveType
import worlds.gregs.hestia.game.plugins.core.components.map.Position
import worlds.gregs.hestia.game.plugins.movement.components.types.Moving
import worlds.gregs.hestia.game.plugins.movement.components.types.MoveStep
import worlds.gregs.hestia.services.Aspect

/**
 * MoveSystem
 * Instantly moves player to [Position]
 * Aka teleporting without delay & animations
 */
class MoveSystem : Move(Aspect.all(Position::class, MoveStep::class)) {
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var moveMapper: ComponentMapper<MoveStep>
    private lateinit var movingMapper: ComponentMapper<Moving>
    private lateinit var shiftMapper: ComponentMapper<Shift>
    private lateinit var es: EventSystem

    override fun process(entityId: Int) {
        val position = positionMapper.get(entityId)
        val move = moveMapper.get(entityId)
//        val lastPlane = position.plane

        //Move to location
        shiftMapper.create(entityId).add(move.x - position.x, move.y - position.y, move.plane - position.plane)

        //Remove command
        moveMapper.remove(entityId)

        //Flag movement type as "move"
        movingMapper.create(entityId)
        es.dispatch(FlagMoveType(entityId))

        /*entity.updateEntityRegion()
        if (needMapUpdate()) {
            entity.map.loadMapRegions()
        } else if (entity is PlayerBlob && lastPlane != entity.plane) {
            entity.clientLoadedMapRegion = false
        }*/
//        steps.reset()
    }

    override fun isMoving(entityId: Int): Boolean {
        return movingMapper.has(entityId)
    }

    override fun hasStep(entityId: Int): Boolean {
        return moveMapper.has(entityId)
    }
}