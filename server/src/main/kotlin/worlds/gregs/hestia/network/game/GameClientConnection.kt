package worlds.gregs.hestia.network.game

import io.netty.channel.ChannelHandler
import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.codec.inbound.ConnectionListener
import worlds.gregs.hestia.GameServer

@ChannelHandler.Sharable
class GameClientConnection : ConnectionListener() {

    override fun connect(session: Session) {
    }

    override fun disconnect(session: Session) {
        if (session.id != -1 && GameServer.server.server!!.entityManager.isActive(session.id)) {
            GameServer.server.server!!.delete(session.id)
        }
    }
}