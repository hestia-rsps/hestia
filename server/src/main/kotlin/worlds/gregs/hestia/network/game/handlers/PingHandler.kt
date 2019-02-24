package worlds.gregs.hestia.network.game.handlers

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageHandler
import world.gregs.hestia.core.network.protocol.decoders.messages.Ping

class PingHandler : MessageHandler<Ping> {

    override fun handle(ctx: ChannelHandlerContext, message: Ping) {
    }

}