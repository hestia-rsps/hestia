package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.TOGGLE_FOCUS
import worlds.gregs.hestia.network.client.decoders.messages.WindowFocus

class WindowFocusDecoder : MessageDecoder<WindowFocus>(1, TOGGLE_FOCUS) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): WindowFocus? {
        return WindowFocus(packet.readBoolean())
    }

}