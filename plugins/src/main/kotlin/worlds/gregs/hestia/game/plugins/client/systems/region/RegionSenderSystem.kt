package worlds.gregs.hestia.game.plugins.client.systems.region

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import net.mostlyoriginal.api.event.common.EventSystem
import net.mostlyoriginal.api.event.common.Subscribe
import world.gregs.hestia.core.network.packets.Packet
import worlds.gregs.hestia.GameConstants
import worlds.gregs.hestia.api.SubscriptionSystem
import worlds.gregs.hestia.api.region.Dynamic
import worlds.gregs.hestia.api.region.Regions
import worlds.gregs.hestia.game.events.UpdateMapRegion
import worlds.gregs.hestia.api.core.components.Position
import worlds.gregs.hestia.api.core.components.Viewport
import worlds.gregs.hestia.network.out.DynamicMapRegion
import worlds.gregs.hestia.network.out.MapRegion
import worlds.gregs.hestia.services.Aspect
import worlds.gregs.hestia.services.send
import worlds.gregs.hestia.services.toArray

@Wire(failOnNull = false)
class RegionSenderSystem : SubscriptionSystem(Aspect.all(Position::class, Viewport::class)) {

    private lateinit var es: EventSystem
    private var regions: Regions? = null
    private var dynamic: Dynamic? = null
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var viewportMapper: ComponentMapper<Viewport>

    override fun inserted(entityId: Int) {
        //Not recommended for dynamic region on login as if too many chunks it'll go over the packet size limit
        send(entityId, true, false)
    }

    private val login: Packet.Builder.(Int) -> Unit = { entityId ->
        val position = positionMapper.get(entityId)
        val viewport = viewportMapper.get(entityId)

        startBitAccess()
        //Send current player position
        writeBits(30, position.locationHash30Bit)

        //Update player locations
        entityIds.toArray().filterNot { it == entityId }.forEach { player ->
            val pos = positionMapper.get(player)
            val hash = pos.locationHash18Bit
            viewport.updatePosition(player, pos)
            writeBits(18, hash)
        }

        //Iterate up to max number of players
        for(i in (entityIds.size() + 1) until GameConstants.PLAYERS_LIMIT) {
            writeBits(18, 0)
        }

        finishBitAccess()
    }

    @Subscribe
    fun send(event: UpdateMapRegion) {
        send(event.entityId, event.local, event.forceRefresh)
    }

    fun send(entityId: Int, local: Boolean, forceRefresh: Boolean) {
        val position = positionMapper.get(entityId)
        val regionEntityId = regions?.getEntityId(position.regionId)
        if(regionEntityId != null && dynamic?.isDynamic(regionEntityId) == true) {
            es.send(entityId, DynamicMapRegion(entityId, position, forceRefresh, if(local) login else null, regions!!, dynamic!!))
        } else {
            es.send(entityId, MapRegion(entityId, position, forceRefresh, if(local) login else null))
        }
    }
}