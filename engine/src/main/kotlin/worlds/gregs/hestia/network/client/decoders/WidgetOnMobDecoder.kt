package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.WIDGET_ON_MOB
import worlds.gregs.hestia.network.client.decoders.messages.WidgetOnMob

class WidgetOnMobDecoder : MessageDecoder<WidgetOnMob>(11, WIDGET_ON_MOB) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): WidgetOnMob? {
        return WidgetOnMob(packet.readShort(Modifier.ADD, Endian.LITTLE), packet.readShort(order = Endian.LITTLE), packet.readShort(order = Endian.LITTLE), packet.readInt(Modifier.INVERSE, Endian.MIDDLE), packet.readBoolean())
    }

}