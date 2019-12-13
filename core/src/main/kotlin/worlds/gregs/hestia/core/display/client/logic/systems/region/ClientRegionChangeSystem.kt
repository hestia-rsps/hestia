package worlds.gregs.hestia.core.display.client.logic.systems.region

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.Subscribe
import worlds.gregs.hestia.artemis.SubscriptionSystem
import worlds.gregs.hestia.core.display.client.model.components.LastLoadedRegion
import worlds.gregs.hestia.core.display.client.model.components.NetworkSession
import worlds.gregs.hestia.core.display.client.model.components.Viewport
import worlds.gregs.hestia.core.display.client.model.events.UpdateMapRegion
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.service.Aspect

/**
 * ClientRegionChangeSystem
 * Sends the initial region on client login
 * Sets [LastLoadedRegion] when subsequent region updates are requested
 */
class ClientRegionChangeSystem : SubscriptionSystem(Aspect.all(NetworkSession::class, LastLoadedRegion::class, Position::class, Viewport::class)) {

    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var lastLoadedRegionMapper: ComponentMapper<LastLoadedRegion>

    override fun inserted(entityId: Int) {
        setLastRegion(entityId)
    }

    @Subscribe
    fun update(event: UpdateMapRegion) {
        setLastRegion(event.entityId)
    }

    private fun setLastRegion(entityId: Int) {
        val position = positionMapper.get(entityId)
        val lastLoadedRegion = lastLoadedRegionMapper.get(entityId)
        lastLoadedRegion.set(position)
    }
}