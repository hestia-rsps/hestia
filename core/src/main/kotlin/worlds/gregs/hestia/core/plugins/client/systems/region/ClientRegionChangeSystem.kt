package worlds.gregs.hestia.core.plugins.client.systems.region

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.Subscribe
import worlds.gregs.hestia.artemis.SubscriptionSystem
import worlds.gregs.hestia.artemis.events.UpdateMapRegion
import worlds.gregs.hestia.api.client.components.NetworkSession
import worlds.gregs.hestia.api.client.components.Viewport
import worlds.gregs.hestia.game.entity.components.Position
import worlds.gregs.hestia.core.plugins.client.components.LastLoadedRegion
import worlds.gregs.hestia.services.Aspect

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