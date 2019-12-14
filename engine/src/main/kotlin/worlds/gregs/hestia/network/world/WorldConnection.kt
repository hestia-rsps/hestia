package worlds.gregs.hestia.network.world

import io.netty.channel.ChannelHandler
import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.codec.ConnectionSessionListener
import world.gregs.hestia.core.network.protocol.Details
import world.gregs.hestia.core.network.protocol.encoders.messages.FriendsChatDisconnect
import world.gregs.hestia.core.network.protocol.messages.WorldInfo
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.artemis.events.send
import worlds.gregs.hestia.network.client.encoders.messages.FriendListDisconnect
import worlds.gregs.hestia.service.forEach
import worlds.gregs.hestia.service.players

@ChannelHandler.Sharable
class WorldConnection(private val details: Details) : ConnectionSessionListener() {

    override fun connect(session: Session) {
        session.write(WorldInfo(details), true)
    }

    override fun disconnect(session: Session) {
        val world = GameServer.server.server ?: return
        world.players().forEach {
            val entity = world.getEntity(it)
            entity.send(FriendListDisconnect())
            entity.send(FriendsChatDisconnect())
        }
    }
}