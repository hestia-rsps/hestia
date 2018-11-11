package world.gregs.hestia.game.systems.direction

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import world.gregs.hestia.game.component.update.direction.Face
import world.gregs.hestia.game.component.update.direction.Facing
import world.gregs.hestia.services.Aspect

/**
 * DirectionSystem
 * Changed the direction which an entity is facing
 */
class DirectionSystem : IteratingSystem(Aspect.all(Face::class)) {
    private lateinit var facingMapper: ComponentMapper<Facing>
    private lateinit var faceMapper: ComponentMapper<Face>

    override fun process(entityId: Int) {
        val face = faceMapper.get(entityId)

        //Set coordinates to face
        val facing = facingMapper.create(entityId)
        facing.x = face.x
        facing.y = face.y

        //Remove request
        faceMapper.remove(entityId)
    }
}