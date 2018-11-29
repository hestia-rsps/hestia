package worlds.gregs.hestia.game.plugins.region.systems

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.game.plugins.region.components.Clean
import worlds.gregs.hestia.game.plugins.region.components.RegionEntities

class RegionEntitySystem : PassiveSystem() {

    private lateinit var regionSystem: RegionSystem
    private lateinit var regionEntitiesMapper: ComponentMapper<RegionEntities>
    private lateinit var cleanMapper: ComponentMapper<Clean>

    fun add(regionId: Int) {
        //Increase count
        val entityId = regionSystem.getEntity(regionId) ?: return
        val entities = regionEntitiesMapper.create(entityId)
        entities.entities++
    }

    fun remove(regionId: Int) {
        val entityId = regionSystem.getEntity(regionId) ?: return
        if(regionEntitiesMapper.has(entityId)) {
            val entities = regionEntitiesMapper.get(entityId)
            if(entities.entities == 1) {
                //Remove last entity
                regionEntitiesMapper.remove(entityId)
                //Flag for cleaning
                if(!cleanMapper.has(entityId)) {
                    cleanMapper.create(entityId)
                }
            } else {
                entities.entities--
            }
        }
    }
}