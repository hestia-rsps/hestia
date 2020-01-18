package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.SWITCH_INTERFACE_COMPONENTS
import worlds.gregs.hestia.network.client.decoders.messages.WindowSwitchWidgets

class WindowSwitchWidgetsDecoder : MessageDecoder<WindowSwitchWidgets>(16, SWITCH_INTERFACE_COMPONENTS) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): WindowSwitchWidgets? {
        return WindowSwitchWidgets(packet.readShort(), packet.readShort(order = Endian.LITTLE), packet.readShort(Modifier.ADD), packet.readInt(order = Endian.MIDDLE), packet.readShort(order = Endian.LITTLE), packet.readInt(Modifier.INVERSE, Endian.MIDDLE))
    }

}