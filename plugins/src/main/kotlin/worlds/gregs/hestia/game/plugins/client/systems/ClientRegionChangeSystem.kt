package worlds.gregs.hestia.game.plugins.client.systems

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import net.mostlyoriginal.api.event.common.Subscribe
import world.gregs.hestia.core.network.packets.Packet
import worlds.gregs.hestia.game.events.UpdateMapRegion
import worlds.gregs.hestia.game.plugins.client.components.LastLoadedRegion
import worlds.gregs.hestia.game.plugins.client.components.NetworkSession
import worlds.gregs.hestia.game.plugins.core.components.map.Position
import worlds.gregs.hestia.game.plugins.core.components.map.Viewport
import worlds.gregs.hestia.game.plugins.core.systems.extensions.SubscriptionSystem
import worlds.gregs.hestia.game.plugins.player.systems.sync.PlayerIndexSystem
import worlds.gregs.hestia.game.plugins.region.components.Dynamic
import worlds.gregs.hestia.game.plugins.region.systems.RegionSystem
import worlds.gregs.hestia.network.out.DynamicMapRegion
import worlds.gregs.hestia.network.out.MapRegion
import worlds.gregs.hestia.services.Aspect
import worlds.gregs.hestia.services.send
import worlds.gregs.hestia.services.toArray

class ClientRegionChangeSystem : SubscriptionSystem(Aspect.all(NetworkSession::class, LastLoadedRegion::class, Position::class, Viewport::class)) {

    private lateinit var es: EventSystem
    private lateinit var viewportMapper: ComponentMapper<Viewport>
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var lastLoadedRegionMapper: ComponentMapper<LastLoadedRegion>
    private lateinit var dynamicMapper: ComponentMapper<Dynamic>//TODO should client have a dependency on regions?
    private lateinit var regionSystem: RegionSystem

    private val login: Packet.Builder.(Int) -> Unit = { entityId ->
        val position = positionMapper.get(entityId)
        val viewport = viewportMapper.get(entityId)

        startBitAccess()
        //Send current player position
        writeBits(30, position.locationHash30Bit)

        //Update player locations
        entityIds.toArray().filterNot { it == entityId }.forEach { player ->
            val hash = positionMapper.get(player).locationHash18Bit
            viewport.updateHash(player, positionMapper.get(player))
            writeBits(18, hash)
        }

        //Iterate up to max number of players
        for(i in (entityIds.size() + 1) until PlayerIndexSystem.PLAYERS_LIMIT) {
            writeBits(18, 0)
        }

        finishBitAccess()
    }

    override fun inserted(entityId: Int) {
        update(entityId, true, false)
    }

    @Subscribe
    fun update(event: UpdateMapRegion) {
        update(event.entityId, event.local, event.forceRefresh)
    }

    private fun update(entityId: Int, local: Boolean, forceRefresh: Boolean) {
        val position = positionMapper.get(entityId)
        val lastLoadedRegion = lastLoadedRegionMapper.get(entityId)
        lastLoadedRegion.set(position)
        val regionEntityId = regionSystem.getEntity(position.regionId) ?: return
        if(dynamicMapper.has(regionEntityId)) {
            //Not recommended for dynamic region on login as if too many chunks it'll go over the packet size limit
            es.send(entityId, DynamicMapRegion(entityId, position, forceRefresh, if(local) login else null, regionSystem, dynamicMapper))
        } else {
            es.send(entityId, MapRegion(entityId, position, forceRefresh, if(local) login else null))
        }
    }
}

val Position.locationHash18Bit: Int
    get() = regionY + (regionX shl 8) + (plane shl 16)

val Position.locationHash30Bit: Int
    get() = y + (x shl 14) + (plane shl 28)
