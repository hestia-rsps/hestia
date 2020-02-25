package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.RECEIVE_COUNT
import worlds.gregs.hestia.network.client.decoders.messages.ReceiveCount

class ReceiveCountDecoder : MessageDecoder<ReceiveCount>(4, RECEIVE_COUNT) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): ReceiveCount? {
        return ReceiveCount(packet.readInt())
    }

}