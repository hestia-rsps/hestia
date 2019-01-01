package worlds.gregs.hestia.game.plugins.region.systems

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.api.land.Land
import worlds.gregs.hestia.api.map.Map
import worlds.gregs.hestia.api.region.Region
import worlds.gregs.hestia.api.region.Regions
import worlds.gregs.hestia.game.events.CreateRegion
import worlds.gregs.hestia.game.plugins.region.components.Loaded
import worlds.gregs.hestia.game.plugins.region.components.Loading
import worlds.gregs.hestia.game.plugins.region.components.RegionIdentifier
import worlds.gregs.hestia.services.Aspect

@Wire(failOnNull = false)
class RegionSystem : Region(Aspect.all(RegionIdentifier::class)) {

    private lateinit var es: EventSystem
    private var regions: Regions? = null
    private var land: Land? = null
    private lateinit var loadingMapper: ComponentMapper<Loading>
    private lateinit var loadedMapper: ComponentMapper<Loaded>
    private var map: Map? = null

    override fun inserted(entityId: Int) {
        //Begin loading process
        loadingMapper.create(entityId)
    }

    override fun load(regionId: Int) {
        if (regions?.contains(regionId) == true) {
            return
        }
        es.dispatch(CreateRegion(regionId))
    }

    override fun unload(entityId: Int) {
        //If loaded
        if(loadedMapper.has(entityId)) {
            //Unload clipping
            map?.unload(entityId)
            //Unload objects
            land?.unload(entityId)
            //Remove loaded flag
            loadedMapper.remove(entityId)
        }
    }
}