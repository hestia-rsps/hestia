package worlds.gregs.hestia.game.plugins.region.systems

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.game.plugins.region.components.Clean
import worlds.gregs.hestia.game.plugins.region.components.Region
import worlds.gregs.hestia.game.plugins.region.components.RegionEntities
import worlds.gregs.hestia.services.Aspect

class RegionCleanSystem : IteratingSystem(Aspect.all(Clean::class)) {
    private lateinit var regionEntitiesMapper: ComponentMapper<RegionEntities>
    private lateinit var regionMapper: ComponentMapper<Region>
    private lateinit var regionSystem: RegionSystem
    private lateinit var cleanMapper: ComponentMapper<Clean>

    override fun process(entityId: Int) {
        cleanMapper.remove(entityId)
        val region = regionMapper.get(entityId)
        //Check all nearby regions
        for(regionX in region.x - 1..region.x + 1) {
            for(regionY in region.y - 1..region.y + 1) {
                val regionId = (regionX shl 8) + regionY
                val entity = regionSystem.getEntity(regionId) ?: continue
                //If has entities
                if(regionEntitiesMapper.has(entity)) {
                    return//Don't clean
                }
            }
        }
        //Clean
        regionSystem.unload(region.id)
    }
}