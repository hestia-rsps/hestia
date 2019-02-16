package worlds.gregs.hestia.network.game

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.MessageHandshakeDispatcher
import world.gregs.hestia.core.network.protocol.handlers.LoginHandshakeHandler
import worlds.gregs.hestia.network.game.handlers.GameLoginHandler
import worlds.gregs.hestia.network.game.handlers.PingHandler

class GameMessages(sessions: HashMap<Int, ChannelHandlerContext>) : MessageHandshakeDispatcher() {
    init {
        bind(GameLoginHandler(sessions), true)
        bind(LoginHandshakeHandler(), true)
        bind(PingHandler())
    }
}