package worlds.gregs.hestia.game.plugins.region.systems

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.game.plugins.region.components.Clean
import worlds.gregs.hestia.game.plugins.region.components.Dynamic
import worlds.gregs.hestia.game.plugins.region.components.Region
import worlds.gregs.hestia.game.plugins.region.components.RegionEntities
import worlds.gregs.hestia.game.plugins.region.systems.RegionBuilder.Companion.forChunks
import worlds.gregs.hestia.game.plugins.region.systems.RegionBuilder.Companion.nearby
import worlds.gregs.hestia.services.Aspect

/**
 * Region Clean System
 * Unloads regions which has no active entities in
 * Entities in this case refers to non-mob's (Players & bots)
 */
class RegionCleanSystem : IteratingSystem(Aspect.all(Clean::class)) {
    private lateinit var regionEntitiesMapper: ComponentMapper<RegionEntities>
    private lateinit var regionMapper: ComponentMapper<Region>
    private lateinit var regionSystem: RegionSystem
    private lateinit var cleanMapper: ComponentMapper<Clean>
    private lateinit var dynamicMapper: ComponentMapper<Dynamic>

    override fun process(entityId: Int) {
        cleanMapper.remove(entityId)
        val region = regionMapper.get(entityId)

        //Stop if region is dynamic
        if (dynamicMapper.has(entityId)) {
            return
        }

        //Check all nearby regions
        forChunks(nearby(region.regionX, 1), nearby(region.regionY, 1)) { regionX, regionY ->
            val regionId = (regionX shl 8) + regionY
            val entity = regionSystem.getEntity(regionId) ?: return@forChunks
            //If has entities
            if (regionEntitiesMapper.has(entity)) {
                return//Don't clean
            }
        }
        //Clean
        regionSystem.unload(region.id)
    }
}