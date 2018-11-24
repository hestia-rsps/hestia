package worlds.gregs.hestia.game.plugins.client.systems.update.bases.update.sync

import com.artemis.ComponentMapper
import world.gregs.hestia.core.network.packets.Packet
import worlds.gregs.hestia.game.plugins.client.components.ClientPlayerPacket
import worlds.gregs.hestia.game.plugins.client.components.update.list.GlobalPlayers
import worlds.gregs.hestia.game.plugins.client.components.update.stage.EntityUpdates
import worlds.gregs.hestia.game.plugins.client.systems.update.bases.update.BaseEntitySyncSystem
import worlds.gregs.hestia.game.plugins.core.components.map.Viewport
import worlds.gregs.hestia.game.update.DisplayFlag
import java.util.*

abstract class BasePlayerSyncSystem : BaseEntitySyncSystem(GlobalPlayers::class, ClientPlayerPacket::class, EntityUpdates::class) {

    private lateinit var viewportMapper: ComponentMapper<Viewport>
    private lateinit var globalPlayersMapper: ComponentMapper<GlobalPlayers>
    private lateinit var clientPlayerPacketMapper: ComponentMapper<ClientPlayerPacket>

    override fun packet(entityId: Int): Packet.Builder {
        return clientPlayerPacketMapper.get(entityId).packet
    }

    override fun locals(entityId: Int): ArrayList<Int> {
        return viewportMapper.get(entityId).localPlayers()
    }

    override fun globals(entityId: Int): ArrayList<Int> {
        return globalPlayersMapper.get(entityId).list
    }

    override fun count(locals: List<Int>, stages: Map<Int, DisplayFlag>): Int {
        return locals.count { stages[it] != DisplayFlag.REMOVE }
    }
}