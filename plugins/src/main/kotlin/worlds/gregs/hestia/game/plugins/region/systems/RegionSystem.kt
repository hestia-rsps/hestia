package worlds.gregs.hestia.game.plugins.region.systems

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.game.events.CreateRegion
import worlds.gregs.hestia.game.plugins.core.systems.extensions.SubscriptionSystem
import worlds.gregs.hestia.game.plugins.region.components.Loaded
import worlds.gregs.hestia.game.plugins.region.components.Loading
import worlds.gregs.hestia.game.plugins.region.components.Objects
import worlds.gregs.hestia.game.plugins.region.components.Region
import worlds.gregs.hestia.services.Aspect
import java.util.*

class RegionSystem : SubscriptionSystem(Aspect.all(Region::class)) {

    private val list = HashMap<Int, Int>()
    private lateinit var es: EventSystem
    private lateinit var regionMapper: ComponentMapper<Region>
    private lateinit var objectsMapper: ComponentMapper<Objects>
    private lateinit var loadingMapper: ComponentMapper<Loading>
    private lateinit var loadedMapper: ComponentMapper<Loaded>

    override fun inserted(entityId: Int) {
        val region = regionMapper.get(entityId)
        list[region.id] = entityId
        loadingMapper.create(entityId)
    }

    override fun removed(entityId: Int) {
        val region = regionMapper.get(entityId)
        list.remove(region.id, entityId)
    }

    fun getEntity(regionId: Int): Int? {
        return list[regionId]
    }

    /**
     * Loads region if hasn't been loaded already
     * @param regionId
     */
    fun load(regionId: Int) {
        if (list.containsKey(regionId)) {
            return
        }

        es.dispatch(CreateRegion(regionId))
    }


    /**
     * Unloads region resources if the region is loaded
     * @param regionId
     */
    fun unload(regionId: Int) {
        val entityId = getEntity(regionId) ?: return
        if (loadedMapper.has(entityId)) {
            val objects = objectsMapper.get(entityId)
            objects.objects = null
            objects.spawnedObjects?.clear()
            objects.removedObjects?.clear()
            loadedMapper.remove(entityId)
        }
    }
}