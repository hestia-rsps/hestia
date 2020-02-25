package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.PING_LATENCY
import worlds.gregs.hestia.network.client.decoders.messages.Latency

class LatencyDecoder : MessageDecoder<Latency>(2, PING_LATENCY) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): Latency? {
        return Latency(packet.readShort())
    }

}