package worlds.gregs.hestia.core.entity.entity.logic.systems.update

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import net.mostlyoriginal.api.event.common.Subscribe
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.core.display.update.model.components.direction.Face
import worlds.gregs.hestia.core.display.update.model.components.direction.FaceUpdate
import worlds.gregs.hestia.core.display.update.model.components.direction.Facing
import worlds.gregs.hestia.core.world.movement.model.components.Shift

/**
 * DirectionSystem
 * Turns entity to the correct direction when moving
 */
class DirectionSystem : IteratingSystem(Aspect.all(Shift::class)) {

    private lateinit var shiftMapper: ComponentMapper<Shift>
    private lateinit var facingMapper: ComponentMapper<Facing>
    private lateinit var faceUpdateMapper: ComponentMapper<FaceUpdate>

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
        val facing = facingMapper.create(action.entity)
        facing.x = action.x
        facing.y = action.y
        faceUpdateMapper.create(action.entity)
    }
}