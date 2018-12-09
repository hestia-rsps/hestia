package worlds.gregs.hestia.game.plugins.client.systems.update.bases.update.sync

import com.artemis.ComponentMapper
import world.gregs.hestia.core.network.packets.Packet
import worlds.gregs.hestia.api.client.components.EntityUpdates
import worlds.gregs.hestia.api.core.components.Viewport
import worlds.gregs.hestia.game.plugins.client.components.ClientMobPacket
import worlds.gregs.hestia.game.plugins.client.components.update.list.GlobalMobs
import worlds.gregs.hestia.game.plugins.client.systems.update.bases.update.BaseEntitySyncSystem
import worlds.gregs.hestia.game.update.DisplayFlag

abstract class BaseMobSyncSystem : BaseEntitySyncSystem(GlobalMobs::class, ClientMobPacket::class, EntityUpdates::class) {

    private lateinit var viewportMapper: ComponentMapper<Viewport>
    private lateinit var globalMobsMapper: ComponentMapper<GlobalMobs>
    private lateinit var clientMobPacketMapper: ComponentMapper<ClientMobPacket>

    override fun packet(entityId: Int): Packet.Builder {
        return clientMobPacketMapper.get(entityId).packet
    }

    override fun locals(entityId: Int): ArrayList<Int> {
        return viewportMapper.get(entityId).localMobs()
    }

    override fun globals(entityId: Int): ArrayList<Int> {
        return globalMobsMapper.get(entityId).list
    }

    override fun count(locals: List<Int>, stages: Map<Int, DisplayFlag>): Int {
        return locals.count { stages[it] != DisplayFlag.REMOVE && stages[it] != DisplayFlag.MOVE }
    }
}
