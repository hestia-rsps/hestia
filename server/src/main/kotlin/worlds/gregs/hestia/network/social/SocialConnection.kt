package worlds.gregs.hestia.network.social

import io.netty.channel.ChannelHandler
import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.codec.inbound.ConnectionListener
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.game.events.send
import worlds.gregs.hestia.network.game.out.DisconnectFriendsChat
import worlds.gregs.hestia.network.game.out.DisconnectFriendsList
import worlds.gregs.hestia.network.social.out.WorldInfo
import worlds.gregs.hestia.services.players

@ChannelHandler.Sharable
class SocialConnection : ConnectionListener() {

    override fun connect(session: Session) {
        session.write(WorldInfo())
    }

    override fun disconnect(session: Session) {
        val world = GameServer.server.server ?: return
        world.players().forEach {
            val entity = world.getEntity(it)
            entity.send(DisconnectFriendsList())
            entity.send(DisconnectFriendsChat())
        }
    }
}