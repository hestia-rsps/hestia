package worlds.gregs.hestia.core.world.map.logic.systems

import com.artemis.ComponentMapper
import worlds.gregs.hestia.core.world.map.api.Clipping
import worlds.gregs.hestia.core.world.map.api.Map
import worlds.gregs.hestia.core.world.map.model.components.ClippingMap

/**
 * MapSystem
 * Handles clipping data
 */
class MapSystem : Map() {
    private lateinit var clippingMapper: ComponentMapper<ClippingMap>

    override fun unload(entityId: Int) {
        //TODO fix clipping maps not reloading
//        clippingMapper.remove(entityId)
    }

    override fun getClipping(entityId: Int?): Clipping? {
        if(entityId != null && clippingMapper.has(entityId)) {
            return clippingMapper.get(entityId)
        }
        return null
    }

    override fun createClipping(entityId: Int): Clipping {
        return clippingMapper.create(entityId)
    }

}