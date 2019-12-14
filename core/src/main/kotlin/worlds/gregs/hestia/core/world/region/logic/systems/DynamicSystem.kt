package worlds.gregs.hestia.core.world.region.logic.systems

import com.artemis.ComponentMapper
import worlds.gregs.hestia.core.world.region.api.Dynamic
import worlds.gregs.hestia.core.world.region.model.components.DynamicRegion

class DynamicSystem : Dynamic() {

    private lateinit var dynamicMapper: ComponentMapper<DynamicRegion>

    override fun isDynamic(entityId: Int): Boolean {
        return dynamicMapper.has(entityId)
    }

    override fun create(entityId: Int): DynamicRegion {
        return dynamicMapper.create(entityId)
    }

    override fun get(entityId: Int): DynamicRegion? {
        return if(dynamicMapper.has(entityId)) {
            dynamicMapper.get(entityId)
        } else {
            null
        }
    }

}