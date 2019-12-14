package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.SCREEN_CHANGE
import worlds.gregs.hestia.network.client.decoders.messages.ScreenChange

class ScreenChangeDecoder : MessageDecoder<ScreenChange>(6, SCREEN_CHANGE) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): ScreenChange? {
        return ScreenChange(packet.readUnsignedByte(), packet.readUnsignedShort(), packet.readUnsignedShort(), packet.readUnsignedByte())
    }

}