package world.gregs.hestia.game.systems.login

import com.artemis.ComponentMapper
import world.gregs.hestia.game.component.NetworkSession
import world.gregs.hestia.game.component.entity.Player
import world.gregs.hestia.game.component.map.Position
import world.gregs.hestia.game.component.map.Viewport
import world.gregs.hestia.game.events.UpdateMapRegion
import world.gregs.hestia.game.systems.extensions.SubscriptionSystem
import world.gregs.hestia.services.Aspect
import net.mostlyoriginal.api.event.common.EventSystem
import net.mostlyoriginal.api.event.common.Subscribe
import world.gregs.hestia.game.component.map.LastLoadedRegion
import java.util.*
import world.gregs.hestia.services.send
import world.gregs.hestia.network.out.MapRegion

class MapRegionSystem : SubscriptionSystem(Aspect.all(NetworkSession::class, Player::class)) {

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
        lastLoadedRegion.x = position.x
        lastLoadedRegion.y = position.y
        lastLoadedRegion.plane = position.plane
        es.send(entityId, MapRegion(list, viewport, positionMapper, entityId, position, local))
    }
}

val Position.locationHash18Bit: Int
    get() = regionY + (regionX shl 8) + (plane shl 16)

val Position.locationHash30Bit: Int
    get() = y + (x shl 14) + (plane shl 28)
