package worlds.gregs.hestia.core.world.region.logic.systems

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.core.world.land.api.Land
import worlds.gregs.hestia.core.world.map.api.Map
import worlds.gregs.hestia.core.world.region.api.Region
import worlds.gregs.hestia.core.world.region.api.Regions
import worlds.gregs.hestia.core.world.region.model.events.CreateRegion
import worlds.gregs.hestia.core.world.region.model.components.Loaded
import worlds.gregs.hestia.core.world.region.model.components.Loading
import worlds.gregs.hestia.core.world.region.model.components.RegionIdentifier
import worlds.gregs.hestia.artemis.Aspect

@Wire(failOnNull = false)
class RegionSystem : Region(Aspect.all(RegionIdentifier::class)) {

    private lateinit var es: EventSystem
    private var regions: Regions? = null
    private var land: Land? = null
    private lateinit var loadingMapper: ComponentMapper<Loading>
    private lateinit var loadedMapper: ComponentMapper<Loaded>
    private lateinit var regionIdentifierMapper: ComponentMapper<RegionIdentifier>
    private var map: Map? = null
    private val loadQueue = ArrayList<Int>()

    override fun inserted(entityId: Int) {
        //Begin loading process
        loadingMapper.create(entityId)
    }

    override fun load(regionId: Int) {
        if (regions?.contains(regionId) == true) {
            return
        }

        if(loadQueue.contains(regionId)) {
            return
        }

        es.dispatch(CreateRegion(regionId))
        loadQueue.add(regionId)
    }

    override fun unload(entityId: Int) {
        //If loaded
        if(loadedMapper.has(entityId)) {
            //Unload collision maps
            map?.unload(entityId)
            //Unload objects
            land?.unload(entityId)
            //Remove loaded flag
            loadedMapper.remove(entityId)
            //Remove from load queue
            loadQueue.remove(regionIdentifierMapper.get(entityId).id)
        }
    }
}