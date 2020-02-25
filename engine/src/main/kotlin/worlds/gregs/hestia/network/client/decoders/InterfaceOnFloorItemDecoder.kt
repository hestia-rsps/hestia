package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.io.Endian
import world.gregs.hestia.io.Modifier
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.INTERFACE_ON_FLOOR_ITEM
import worlds.gregs.hestia.network.client.decoders.messages.InterfaceOnFloorItem

class InterfaceOnFloorItemDecoder : MessageDecoder<InterfaceOnFloorItem>(15, INTERFACE_ON_FLOOR_ITEM) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): InterfaceOnFloorItem? {
        return InterfaceOnFloorItem(packet.readShort(), packet.readShort(), packet.readShort(Modifier.ADD, Endian.LITTLE), packet.readInt(Modifier.INVERSE, Endian.MIDDLE), packet.readShort(order = Endian.LITTLE), packet.readBoolean(), packet.readShort(order = Endian.LITTLE))
    }

}