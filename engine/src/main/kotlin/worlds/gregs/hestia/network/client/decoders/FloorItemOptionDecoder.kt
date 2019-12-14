package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.DataType
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.FLOOR_ITEM_OPTION_1
import world.gregs.hestia.core.network.protocol.ClientOpcodes.FLOOR_ITEM_OPTION_2
import world.gregs.hestia.core.network.protocol.ClientOpcodes.FLOOR_ITEM_OPTION_3
import world.gregs.hestia.core.network.protocol.ClientOpcodes.FLOOR_ITEM_OPTION_4
import world.gregs.hestia.core.network.protocol.ClientOpcodes.FLOOR_ITEM_OPTION_5
import world.gregs.hestia.core.network.protocol.ClientOpcodes.FLOOR_ITEM_OPTION_6
import worlds.gregs.hestia.network.client.decoders.messages.FloorItemOptionMessage

class FloorItemOptionDecoder : MessageDecoder<FloorItemOptionMessage>(7, FLOOR_ITEM_OPTION_1, FLOOR_ITEM_OPTION_2, FLOOR_ITEM_OPTION_3, FLOOR_ITEM_OPTION_4, FLOOR_ITEM_OPTION_5, FLOOR_ITEM_OPTION_6) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): FloorItemOptionMessage? {
        return FloorItemOptionMessage(packet.readUnsigned(DataType.SHORT, Modifier.ADD).toInt(), packet.readBoolean(), packet.readShort(), packet.readShort(order = Endian.LITTLE), opcodes.indexOf(packet.opcode) + 1)
    }

}