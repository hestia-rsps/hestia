package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.DataType
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.OBJECT_OPTION_1
import world.gregs.hestia.core.network.protocol.ClientOpcodes.OBJECT_OPTION_2
import world.gregs.hestia.core.network.protocol.ClientOpcodes.OBJECT_OPTION_3
import world.gregs.hestia.core.network.protocol.ClientOpcodes.OBJECT_OPTION_4
import world.gregs.hestia.core.network.protocol.ClientOpcodes.OBJECT_OPTION_5
import world.gregs.hestia.core.network.protocol.ClientOpcodes.OBJECT_OPTION_6
import worlds.gregs.hestia.network.client.decoders.messages.ObjectOption

class ObjectOptionDecoder : MessageDecoder<ObjectOption>(7, OBJECT_OPTION_1, OBJECT_OPTION_2, OBJECT_OPTION_3, OBJECT_OPTION_4, OBJECT_OPTION_5, OBJECT_OPTION_6) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): ObjectOption? {
        val run = packet.readBoolean(Modifier.ADD)
        val x = packet.readShort(Modifier.ADD)
        val id = packet.readUnsigned(DataType.SHORT, Modifier.ADD, Endian.LITTLE).toInt()
        val y = packet.readShort(order = Endian.LITTLE)
        val option = opcodes.indexOf(packet.opcode) + 1
        return ObjectOption(id, x, y, run, option)
    }

}