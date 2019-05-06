package worlds.gregs.hestia.network.client

import io.netty.channel.ChannelHandler
import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.codec.ConnectionSessionListener
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.artemis.events.Disconnect

@ChannelHandler.Sharable
class ClientConnection : ConnectionSessionListener() {

    override fun connect(session: Session) {
    }

    override fun disconnect(session: Session) {
        if (session.id != -1 && GameServer.server.server!!.entityManager.isActive(session.id)) {
            GameServer.eventSystem.dispatch(Disconnect(session.id))
        }
    }
}