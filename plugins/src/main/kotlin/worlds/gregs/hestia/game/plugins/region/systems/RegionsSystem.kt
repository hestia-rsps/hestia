package worlds.gregs.hestia.game.plugins.region.systems

import com.artemis.ComponentMapper
import worlds.gregs.hestia.api.region.Regions
import worlds.gregs.hestia.game.plugins.region.components.RegionIdentifier
import worlds.gregs.hestia.services.Aspect
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