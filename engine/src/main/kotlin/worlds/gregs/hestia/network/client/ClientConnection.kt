package worlds.gregs.hestia.network.client

import io.netty.channel.ChannelHandler
import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.codec.ConnectionSessionListener
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.artemis.events.Disconnect
import worlds.gregs.hestia.game

@ChannelHandler.Sharable
class ClientConnection : ConnectionSessionListener() {

    override fun connect(session: Session) {
    }

    override fun disconnect(session: Session) {
        if (session.id != -1 && game.entityManager.isActive(session.id)) {
            GameServer.eventSystem.dispatch(Disconnect(session.id))
        }
    }
}