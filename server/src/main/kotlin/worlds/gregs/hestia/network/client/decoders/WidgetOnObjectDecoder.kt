package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.ITEM_ON_OBJECT
import worlds.gregs.hestia.network.client.decoders.messages.WidgetOnObject

class WidgetOnObjectDecoder : MessageDecoder<WidgetOnObject>(15, ITEM_ON_OBJECT) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): WidgetOnObject? {
        return WidgetOnObject(packet.readBoolean(Modifier.INVERSE), packet.readShort(order = Endian.LITTLE), packet.readShort(order = Endian.LITTLE), packet.readInt(order = Endian.LITTLE), packet.readShort(Modifier.ADD, Endian.LITTLE), packet.readShort(order = Endian.LITTLE), packet.readShort(Modifier.ADD))
    }

}