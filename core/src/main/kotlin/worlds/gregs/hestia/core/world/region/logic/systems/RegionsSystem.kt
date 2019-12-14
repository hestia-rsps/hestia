package worlds.gregs.hestia.core.world.region.logic.systems

import com.artemis.ComponentMapper
import worlds.gregs.hestia.core.world.region.api.Regions
import worlds.gregs.hestia.core.world.region.model.components.RegionIdentifier
import worlds.gregs.hestia.service.Aspect
import java.util.*

/**
 * RegionsSystem
 * Keeps track of regions for easy access to a regions entity id
 */
class RegionsSystem : Regions(Aspect.all(RegionIdentifier::class)) {

    private val list = HashMap<Int, Int>()
    private lateinit var regionIdentifierMapper: ComponentMapper<RegionIdentifier>

    override fun getEntityId(regionId: Int): Int? {
        return list[regionId]
    }

    override fun contains(regionId: Int): Boolean {
        return list.containsKey(regionId)
    }

    override fun inserted(entityId: Int) {
        val region = regionIdentifierMapper.get(entityId)
        list[region.id] = entityId
    }

    override fun removed(entityId: Int) {
        val region = regionIdentifierMapper.get(entityId)
        list.remove(region.id, entityId)
    }

}