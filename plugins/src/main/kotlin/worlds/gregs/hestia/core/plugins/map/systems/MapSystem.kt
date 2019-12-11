package worlds.gregs.hestia.core.plugins.map.systems

import com.artemis.ComponentMapper
import worlds.gregs.hestia.api.map.Clipping
import worlds.gregs.hestia.api.map.Map
import worlds.gregs.hestia.core.plugins.map.components.ClippingMap
import worlds.gregs.hestia.core.plugins.map.components.ProjectileClipping

/**
 * MapSystem
 * Handles clipping data
 */
class MapSystem : Map() {
    private lateinit var clippingMapper: ComponentMapper<ClippingMap>
    private lateinit var projectileMapper: ComponentMapper<ProjectileClipping>

    override fun unload(entityId: Int) {
        //TODO fix clipping maps not reloading
//        projectileMapper.remove(entityId)
//        clippingMapper.remove(entityId)
    }

    override fun getClipping(entityId: Int?): Clipping? {
        if(entityId != null && clippingMapper.has(entityId)) {
            return clippingMapper.get(entityId)
        }
        return null
    }

    override fun getProjectileMap(entityId: Int?): Clipping? {
        if(entityId != null && projectileMapper.has(entityId)) {
            return projectileMapper.get(entityId)
        }
        return null
    }

    override fun createClipping(entityId: Int): Clipping {
        return clippingMapper.create(entityId)
    }

    override fun createProjectileMap(entityId: Int): Clipping {
        return projectileMapper.create(entityId)
    }

}