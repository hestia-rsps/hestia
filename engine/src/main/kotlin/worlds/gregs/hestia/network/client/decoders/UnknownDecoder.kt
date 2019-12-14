package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.UNKNOWN
import worlds.gregs.hestia.network.client.decoders.messages.Unknown

class UnknownDecoder : MessageDecoder<Unknown>(2, UNKNOWN) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): Unknown? {
        return Unknown(packet.readShort())
    }

}