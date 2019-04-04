package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.WIDGET_ON_FLOOR_ITEM
import worlds.gregs.hestia.network.client.decoders.messages.WidgetOnFloorItem

class WidgetOnFloorItemDecoder : MessageDecoder<WidgetOnFloorItem>(15, WIDGET_ON_FLOOR_ITEM) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): WidgetOnFloorItem? {
        return WidgetOnFloorItem(packet.readShort(), packet.readShort(), packet.readShort(Modifier.ADD, Endian.LITTLE), packet.readInt(Modifier.INVERSE, Endian.MIDDLE), packet.readShort(order = Endian.LITTLE), packet.readBoolean(), packet.readShort(order = Endian.LITTLE))
    }

}