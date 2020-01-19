package worlds.gregs.hestia.core.entity.entity.logic.systems.update

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import net.mostlyoriginal.api.event.common.Subscribe
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.core.display.update.model.components.direction.Face
import worlds.gregs.hestia.core.display.update.model.components.direction.FaceUpdate
import worlds.gregs.hestia.core.display.update.model.components.direction.Facing
import worlds.gregs.hestia.core.display.update.model.components.direction.Turn
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.world.movement.model.components.Shift

/**
 * DirectionSystem
 * Turns entity to the correct direction when moving
 */
class DirectionSystem : IteratingSystem(Aspect.all(Shift::class)) {

    private lateinit var shiftMapper: ComponentMapper<Shift>
    private lateinit var facingMapper: ComponentMapper<Facing>
    private lateinit var faceUpdateMapper: ComponentMapper<FaceUpdate>
    private lateinit var positionMapper: ComponentMapper<Position>

    override fun process(entityId: Int) {
        val shift = shiftMapper.get(entityId)
        if(shift.x != 0 || shift.y != 0) {
            val face = facingMapper.create(entityId)
            face.x = shift.x
            face.y = shift.y
        }
    }

    @Subscribe
    fun face(action: Face) {
        val position = positionMapper.get(action.entity)
        val deltaX = action.x - getFaceX(position, action.sizeX)
        val deltaY = action.y - getFaceY(position, action.sizeY)
        turn(action.entity, deltaX, deltaY)
    }

    @Subscribe
    fun turn(action: Turn) {
        turn(action.entity, action.direction.deltaX, action.direction.deltaY)
    }

    private fun turn(entityId: Int, deltaX: Int, deltaY: Int) {
        val facing = facingMapper.create(entityId)
        facing.x = deltaX
        facing.y = deltaY
        faceUpdateMapper.create(entityId)
    }

    companion object {
        fun getFaceX(position: Position, sizeX: Int, sizeY: Int = -1, rotation: Int = -1): Int {
            return position.x + ((if (rotation == 1 || rotation == 3) sizeY else sizeX) - 1) / 2
        }

        fun getFaceY(position: Position, sizeY: Int, sizeX: Int = -1, rotation: Int = -1): Int {
            return position.y + ((if (rotation == 1 || rotation == 3) sizeX else sizeY) - 1) / 2
        }
    }
}