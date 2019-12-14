package worlds.gregs.hestia.core.display.client.logic.systems.region

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.core.display.client.model.components.LastLoadedRegion
import worlds.gregs.hestia.core.display.client.model.components.NetworkSession
import worlds.gregs.hestia.core.display.client.model.events.UpdateMapRegion
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.world.map.model.MapConstants.MAP_SIZES
import worlds.gregs.hestia.artemis.Aspect
import kotlin.math.abs

/**
 * ClientRegionLoadSystem
 * Sends map region updates to the client
 */
class ClientRegionLoadSystem : IteratingSystem(Aspect.all(NetworkSession::class, LastLoadedRegion::class)) {

    private lateinit var es: EventSystem
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var lastLoadedRegionMapper: ComponentMapper<LastLoadedRegion>

    override fun process(entityId: Int) {
        val position = positionMapper.get(entityId)
        val lastRegion = lastLoadedRegionMapper.get(entityId)
        val size = (MAP_SIZES[0] shr 4) - 1
        //If needs region update
        if(abs(lastRegion.chunkX - position.chunkX) >= size || abs(lastRegion.chunkY - position.chunkY) >= size) {
            //Set last loaded position
            lastRegion.set(position)

            //Send client map update
            es.dispatch(UpdateMapRegion(entityId, local = false, forceRefresh = false))
        }
    }
}