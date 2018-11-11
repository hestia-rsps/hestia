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
import java.util.*
import world.gregs.hestia.services.send
import world.gregs.hestia.network.out.MapRegion

class MapRegionSystem : SubscriptionSystem(Aspect.all(NetworkSession::class, Player::class)) {

    private lateinit var es: EventSystem
    private lateinit var viewportMapper: ComponentMapper<Viewport>
    private lateinit var positionMapper: ComponentMapper<Position>

    override fun inserted(entityId: Int) {
        update(entityId)
    }

    @Subscribe
    fun update(event: UpdateMapRegion) {
        update(event.entityId)
    }

    private fun update(entityId: Int) {
        val position = positionMapper.get(entityId)
        val viewport = viewportMapper.get(entityId)
        val list = Arrays.copyOf(entityIds.data, entityIds.size())
        es.send(entityId, MapRegion(list, viewport, positionMapper, entityId, position, true))
    }

    companion object {
        val locationHashes = HashMap<Int, Int>()

        fun updateHash(index: Int, tile: Position) {
            locationHashes[index] = tile.locationHash18Bit
        }

        fun getHash(playerIndex: Int): Int {
            return locationHashes[playerIndex] ?: Position.EMPTY.locationHash18Bit
        }
    }

}

val Position.locationHash18Bit: Int
    get() = regionY + (regionX shl 8) + (plane shl 16)

val Position.locationHash30Bit: Int
    get() = y + (x shl 14) + (plane shl 28)
