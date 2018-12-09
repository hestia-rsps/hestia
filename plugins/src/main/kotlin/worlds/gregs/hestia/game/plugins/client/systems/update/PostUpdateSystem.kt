package worlds.gregs.hestia.game.plugins.client.systems.update

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import world.gregs.hestia.core.network.packets.Packet
import worlds.gregs.hestia.game.plugins.client.components.ClientMobPacket
import worlds.gregs.hestia.game.plugins.client.components.ClientPlayerPacket
import worlds.gregs.hestia.game.plugins.client.components.NetworkSession
import worlds.gregs.hestia.game.plugins.client.components.update.list.GlobalMobs
import worlds.gregs.hestia.game.plugins.client.components.update.list.GlobalPlayers
import worlds.gregs.hestia.api.client.components.EntityUpdates
import worlds.gregs.hestia.api.core.components.Viewport
import worlds.gregs.hestia.services.Aspect

class PostUpdateSystem : IteratingSystem(Aspect.all(NetworkSession::class, Viewport::class)) {

    private lateinit var globalPlayersMapper: ComponentMapper<GlobalPlayers>
    private lateinit var globalMobsMapper: ComponentMapper<GlobalMobs>
    private lateinit var clientPlayerPacketMapper: ComponentMapper<ClientPlayerPacket>
    private lateinit var clientMobPacketMapper: ComponentMapper<ClientMobPacket>
    private lateinit var entityUpdatesMapper: ComponentMapper<EntityUpdates>

    override fun process(entityId: Int) {
        globalMobsMapper.get(entityId).list.clear()
        globalPlayersMapper.get(entityId).list.clear()
        clientPlayerPacketMapper.get(entityId).packet = Packet.Builder(69, Packet.Type.VAR_SHORT)
        clientMobPacketMapper.get(entityId).packet = Packet.Builder(6, Packet.Type.VAR_SHORT)
        val entityUpdates = entityUpdatesMapper.get(entityId)
        entityUpdates.map.clear()
        entityUpdates.list.clear()
    }

}