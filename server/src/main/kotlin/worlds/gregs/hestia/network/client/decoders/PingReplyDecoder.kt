package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.PING_REPLY
import worlds.gregs.hestia.network.client.decoders.messages.PingReply

class PingReplyDecoder : MessageDecoder<PingReply>(8, PING_REPLY) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): PingReply? {
        return PingReply(packet.readInt(), packet.readInt())
    }

}