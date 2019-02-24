package worlds.gregs.hestia.network.game.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.WIDGET_BUTTON_1
import world.gregs.hestia.core.network.protocol.ClientOpcodes.WIDGET_BUTTON_10
import world.gregs.hestia.core.network.protocol.ClientOpcodes.WIDGET_BUTTON_2
import world.gregs.hestia.core.network.protocol.ClientOpcodes.WIDGET_BUTTON_3
import world.gregs.hestia.core.network.protocol.ClientOpcodes.WIDGET_BUTTON_4
import world.gregs.hestia.core.network.protocol.ClientOpcodes.WIDGET_BUTTON_5
import world.gregs.hestia.core.network.protocol.ClientOpcodes.WIDGET_BUTTON_6
import world.gregs.hestia.core.network.protocol.ClientOpcodes.WIDGET_BUTTON_7
import world.gregs.hestia.core.network.protocol.ClientOpcodes.WIDGET_BUTTON_8
import world.gregs.hestia.core.network.protocol.ClientOpcodes.WIDGET_BUTTON_9
import worlds.gregs.hestia.network.game.decoders.messages.WidgetClick

class WidgetClickDecoder : MessageDecoder<WidgetClick>(8, WIDGET_BUTTON_1, WIDGET_BUTTON_2, WIDGET_BUTTON_3, WIDGET_BUTTON_4, WIDGET_BUTTON_5, WIDGET_BUTTON_6, WIDGET_BUTTON_7, WIDGET_BUTTON_8, WIDGET_BUTTON_9, WIDGET_BUTTON_10) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): WidgetClick? {
        return WidgetClick(packet.readInt(Modifier.INVERSE, Endian.MIDDLE), packet.readShort(Modifier.ADD, Endian.LITTLE), packet.readShort(), when (packet.opcode) {
            WIDGET_BUTTON_1 -> 1
            WIDGET_BUTTON_2 -> 2
            WIDGET_BUTTON_3 -> 3
            WIDGET_BUTTON_4 -> 4
            WIDGET_BUTTON_5 -> 5
            WIDGET_BUTTON_6 -> 6
            WIDGET_BUTTON_7 -> 7
            WIDGET_BUTTON_8 -> 8
            WIDGET_BUTTON_9 -> 9
            WIDGET_BUTTON_10 -> 10
            else -> 0
        })
    }

}