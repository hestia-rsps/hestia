package worlds.gregs.hestia.network.world

import io.netty.channel.ChannelHandler
import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.codec.ConnectionListener
import world.gregs.hestia.core.network.protocol.Details
import world.gregs.hestia.core.network.protocol.encoders.messages.FriendsChatDisconnect
import world.gregs.hestia.core.network.protocol.messages.WorldInfo
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.game.events.send
import worlds.gregs.hestia.network.game.encoders.messages.FriendListDisconnect
import worlds.gregs.hestia.services.players

@ChannelHandler.Sharable
class WorldConnection(private val details: Details) : ConnectionListener() {

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