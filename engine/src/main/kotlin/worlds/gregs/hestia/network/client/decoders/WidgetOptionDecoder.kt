package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.WIDGET_OPTION_1
import world.gregs.hestia.core.network.protocol.ClientOpcodes.WIDGET_OPTION_10
import world.gregs.hestia.core.network.protocol.ClientOpcodes.WIDGET_OPTION_2
import world.gregs.hestia.core.network.protocol.ClientOpcodes.WIDGET_OPTION_3
import world.gregs.hestia.core.network.protocol.ClientOpcodes.WIDGET_OPTION_4
import world.gregs.hestia.core.network.protocol.ClientOpcodes.WIDGET_OPTION_5
import world.gregs.hestia.core.network.protocol.ClientOpcodes.WIDGET_OPTION_6
import world.gregs.hestia.core.network.protocol.ClientOpcodes.WIDGET_OPTION_7
import world.gregs.hestia.core.network.protocol.ClientOpcodes.WIDGET_OPTION_8
import world.gregs.hestia.core.network.protocol.ClientOpcodes.WIDGET_OPTION_9
import worlds.gregs.hestia.network.client.decoders.messages.WidgetOption

class WidgetOptionDecoder : MessageDecoder<WidgetOption>(8, WIDGET_OPTION_1, WIDGET_OPTION_2, WIDGET_OPTION_3, WIDGET_OPTION_4, WIDGET_OPTION_5, WIDGET_OPTION_6, WIDGET_OPTION_7, WIDGET_OPTION_8, WIDGET_OPTION_9, WIDGET_OPTION_10) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): WidgetOption? {
        return WidgetOption(packet.readInt(Modifier.INVERSE, Endian.MIDDLE), packet.readShort(Modifier.ADD, Endian.LITTLE), packet.readShort(), opcodes.indexOf(packet.opcode) + 1)
    }

}