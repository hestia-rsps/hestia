package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.IN_OUT_SCREEN
import worlds.gregs.hestia.network.client.decoders.messages.WindowHovered

class WindowHoveredDecoder : MessageDecoder<WindowHovered>(4, IN_OUT_SCREEN) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): WindowHovered? {
        return WindowHovered(packet.readBoolean())
    }

}