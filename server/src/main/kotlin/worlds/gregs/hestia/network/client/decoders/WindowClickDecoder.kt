package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.CLICK
import worlds.gregs.hestia.network.client.decoders.messages.WindowClick

class WindowClickDecoder : MessageDecoder<WindowClick>(6, CLICK) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): WindowClick? {
        return WindowClick(packet.readShort(Modifier.ADD, Endian.LITTLE), packet.readInt(order = Endian.MIDDLE))
    }

}