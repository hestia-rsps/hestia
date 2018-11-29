package worlds.gregs.hestia.game.plugins.client.systems

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import net.mostlyoriginal.api.event.common.Subscribe
import worlds.gregs.hestia.game.events.UpdateMapRegion
import worlds.gregs.hestia.game.plugins.client.components.LastLoadedRegion
import worlds.gregs.hestia.game.plugins.client.components.NetworkSession
import worlds.gregs.hestia.game.plugins.core.components.map.Position
import worlds.gregs.hestia.game.plugins.core.components.map.Viewport
import worlds.gregs.hestia.game.plugins.core.systems.extensions.SubscriptionSystem
import worlds.gregs.hestia.network.out.MapRegion
import worlds.gregs.hestia.services.Aspect
import worlds.gregs.hestia.services.send
import java.util.*

class ClientRegionChangeSystem : SubscriptionSystem(Aspect.all(NetworkSession::class, LastLoadedRegion::class, Position::class, Viewport::class)) {

    private lateinit var es: EventSystem
    private lateinit var viewportMapper: ComponentMapper<Viewport>
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var lastLoadedRegionMapper: ComponentMapper<LastLoadedRegion>

    override fun inserted(entityId: Int) {
        update(entityId, true)
    }

    @Subscribe
    fun update(event: UpdateMapRegion) {
        update(event.entityId, event.local)
    }

    private fun update(entityId: Int, local: Boolean) {
        val position = positionMapper.get(entityId)
        val viewport = viewportMapper.get(entityId)
        val list = Arrays.copyOf(entityIds.data, entityIds.size())
        val lastLoadedRegion = lastLoadedRegionMapper.get(entityId)
        lastLoadedRegion.set(position)
        es.send(entityId, MapRegion(list, viewport, positionMapper, entityId, position, local))
    }
}

val Position.locationHash18Bit: Int
    get() = regionY + (regionX shl 8) + (plane shl 16)

val Position.locationHash30Bit: Int
    get() = y + (x shl 14) + (plane shl 28)
