package worlds.gregs.hestia.core.world.map.logic.systems

import com.artemis.ComponentMapper
import worlds.gregs.hestia.core.world.map.api.Collisions
import worlds.gregs.hestia.core.world.map.api.Map
import worlds.gregs.hestia.core.world.map.model.components.CollisionMap

/**
 * MapSystem
 * Handles collision data
 */
class MapSystem : Map() {
    private lateinit var collisionMapper: ComponentMapper<CollisionMap>

    override fun unload(entityId: Int) {
        //TODO fix collision maps not reloading
//        collisionMapper.remove(entityId)
    }

    override fun getCollision(entityId: Int?): Collisions? {
        if(entityId != null && collisionMapper.has(entityId)) {
            return collisionMapper.get(entityId)
        }
        return null
    }

    override fun createCollision(entityId: Int): Collisions {
        return collisionMapper.create(entityId)
    }

}