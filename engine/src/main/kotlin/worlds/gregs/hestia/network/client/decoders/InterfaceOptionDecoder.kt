package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.INTERFACE_OPTION_1
import world.gregs.hestia.core.network.protocol.ClientOpcodes.INTERFACE_OPTION_10
import world.gregs.hestia.core.network.protocol.ClientOpcodes.INTERFACE_OPTION_2
import world.gregs.hestia.core.network.protocol.ClientOpcodes.INTERFACE_OPTION_3
import world.gregs.hestia.core.network.protocol.ClientOpcodes.INTERFACE_OPTION_4
import world.gregs.hestia.core.network.protocol.ClientOpcodes.INTERFACE_OPTION_5
import world.gregs.hestia.core.network.protocol.ClientOpcodes.INTERFACE_OPTION_6
import world.gregs.hestia.core.network.protocol.ClientOpcodes.INTERFACE_OPTION_7
import world.gregs.hestia.core.network.protocol.ClientOpcodes.INTERFACE_OPTION_8
import world.gregs.hestia.core.network.protocol.ClientOpcodes.INTERFACE_OPTION_9
import worlds.gregs.hestia.network.client.decoders.messages.InterfaceOption

class InterfaceOptionDecoder : MessageDecoder<InterfaceOption>(8, INTERFACE_OPTION_1, INTERFACE_OPTION_2, INTERFACE_OPTION_3, INTERFACE_OPTION_4, INTERFACE_OPTION_5, INTERFACE_OPTION_6, INTERFACE_OPTION_7, INTERFACE_OPTION_8, INTERFACE_OPTION_9, INTERFACE_OPTION_10) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): InterfaceOption? {
        return InterfaceOption(packet.readInt(Modifier.INVERSE, Endian.MIDDLE), packet.readShort(Modifier.ADD, Endian.LITTLE), packet.readShort(), opcodes.indexOf(packet.opcode) + 1)
    }

}