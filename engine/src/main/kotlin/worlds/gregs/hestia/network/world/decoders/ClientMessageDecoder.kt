package worlds.gregs.hestia.network.world.decoders

import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.codec.packet.PacketReader
import world.gregs.hestia.core.network.protocol.WorldOpcodes.CLIENT_MESSAGE
import worlds.gregs.hestia.network.world.decoders.messages.ClientPacketOut

class ClientMessageDecoder : MessageDecoder<ClientPacketOut>(Packet.Type.VAR_SHORT, CLIENT_MESSAGE) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): ClientPacketOut? {
        return ClientPacketOut(packet.readInt(), PacketReader(buffer = Unpooled.copiedBuffer(packet.buffer.discardReadBytes())))
    }

}