package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.ENTER_INTEGER
import worlds.gregs.hestia.network.client.decoders.messages.IntegerEntry

class IntegerEntryDecoder : MessageDecoder<IntegerEntry>(4, ENTER_INTEGER) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): IntegerEntry? {
        return IntegerEntry(packet.readInt())
    }

}