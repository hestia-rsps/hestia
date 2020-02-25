package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.io.DataType
import world.gregs.hestia.io.Endian
import world.gregs.hestia.io.Modifier
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.ITEM_ON_OBJECT
import worlds.gregs.hestia.network.client.decoders.messages.InterfaceOnObject

class InterfaceOnObjectDecoder : MessageDecoder<InterfaceOnObject>(15, ITEM_ON_OBJECT) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): InterfaceOnObject? {
        return InterfaceOnObject(packet.readBoolean(Modifier.INVERSE), packet.readShort(order = Endian.LITTLE), packet.readShort(order = Endian.LITTLE), packet.readInt(order = Endian.LITTLE), packet.readShort(Modifier.ADD, Endian.LITTLE), packet.readShort(order = Endian.LITTLE), packet.readUnsigned(DataType.SHORT, Modifier.ADD).toInt())
    }

}