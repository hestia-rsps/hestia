package worlds.gregs.hestia.game.systems.direction

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.game.component.update.direction.Face
import worlds.gregs.hestia.game.component.movement.ShiftPosition
import worlds.gregs.hestia.services.Aspect

/**
 * MovementFaceSystem
 * Turns entity to the correct direction when moving
 */
class MovementFaceSystem : IteratingSystem(Aspect.all(ShiftPosition::class)) {

    private lateinit var shiftPositionMapper: ComponentMapper<ShiftPosition>
    private lateinit var faceMapper: ComponentMapper<Face>

    override fun process(entityId: Int) {
        val shiftPosition = shiftPositionMapper.get(entityId)

        val face = faceMapper.create(entityId)
        face.x = shiftPosition.x
        face.y = shiftPosition.y
    }
}