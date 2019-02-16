package worlds.gregs.hestia.network.client

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.MessageHandshakeDispatcher
import world.gregs.hestia.core.network.protocol.handlers.LoginHandshakeHandler
import worlds.gregs.hestia.network.client.handlers.GameLoginHandler
import worlds.gregs.hestia.network.client.handlers.PingHandler

class ClientMessages(sessions: HashMap<Int, ChannelHandlerContext>) : MessageHandshakeDispatcher() {
    init {
        bind(GameLoginHandler(sessions), true)
        bind(LoginHandshakeHandler(), true)
        bind(PingHandler())
    }
}