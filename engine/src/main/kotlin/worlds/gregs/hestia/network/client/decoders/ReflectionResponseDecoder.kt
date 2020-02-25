package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.REFLECTION_RESPONSE
import worlds.gregs.hestia.network.client.decoders.messages.ReflectionResponse

class ReflectionResponseDecoder : MessageDecoder<ReflectionResponse>(Packet.Type.VAR_BYTE, REFLECTION_RESPONSE) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): ReflectionResponse? {
        packet.readByte()//0
        TODO("not implemented")
    }
}