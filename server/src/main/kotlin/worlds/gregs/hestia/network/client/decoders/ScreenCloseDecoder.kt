package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.SCREEN_CLOSE
import worlds.gregs.hestia.network.client.decoders.messages.ScreenClose

class ScreenCloseDecoder : MessageDecoder<ScreenClose>(0, SCREEN_CLOSE) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): ScreenClose? {
        return ScreenClose()
    }

}