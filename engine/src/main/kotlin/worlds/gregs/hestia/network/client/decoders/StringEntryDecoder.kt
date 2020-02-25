package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.STRING_ENTRY
import worlds.gregs.hestia.network.client.decoders.messages.StringEntry

class StringEntryDecoder : MessageDecoder<StringEntry>(Packet.Type.VAR_BYTE, STRING_ENTRY) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): StringEntry? {
        return StringEntry(packet.readString())
    }

}