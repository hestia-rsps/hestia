package worlds.gregs.hestia.core.world.region.logic.systems

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.artemis.SubscriptionSystem
import worlds.gregs.hestia.artemis.nearby
import worlds.gregs.hestia.core.world.region.api.Region
import worlds.gregs.hestia.core.world.region.api.Regions
import worlds.gregs.hestia.core.world.region.logic.systems.RegionBuilderSystem.Companion.forChunks
import worlds.gregs.hestia.core.world.region.model.components.DynamicRegion
import worlds.gregs.hestia.core.world.region.model.components.RegionIdentifier
import worlds.gregs.hestia.core.world.region.model.components.RegionPriorities

/**
 * Region Clean System
 * Unloads regions which has no prioritised entities in
 * Entities in this case refers to non-npc's (Players & bots)
 */
@Wire(failOnNull = false)
class RegionCleanSystem : SubscriptionSystem(Aspect.all(RegionPriorities::class)) {
    private lateinit var regionPrioritiesMapper: ComponentMapper<RegionPriorities>
    private lateinit var regionMapper: ComponentMapper<RegionIdentifier>
    private lateinit var dynamicMapper: ComponentMapper<DynamicRegion>
    private var regions: Regions? = null
    private var region: Region? = null

    override fun removed(entityId: Int) {
        val region = regionMapper.get(entityId)

        //Stop if region is dynamic
        if (dynamicMapper.has(entityId)) {
            return
        }

        //Check all nearby regions
        forChunks(region.regionX.nearby(1), region.regionY.nearby(1)) { regionX, regionY ->
            val regionId = (regionX shl 8) + regionY
            val entity = regions?.getEntityId(regionId) ?: return@forChunks
            //Don't sync if has priorities
            if (regionPrioritiesMapper.has(entity)) {
                return
            }
        }

        //Clean region
        this.region?.unload(entityId)
    }
}