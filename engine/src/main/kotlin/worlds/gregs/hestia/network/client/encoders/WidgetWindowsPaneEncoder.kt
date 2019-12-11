package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.WIDGET_WINDOW
import worlds.gregs.hestia.network.client.encoders.messages.WidgetWindowsPane

class WidgetWindowsPaneEncoder : MessageEncoder<WidgetWindowsPane>() {

    override fun encode(builder: PacketBuilder, message: WidgetWindowsPane) {
        val (id, type) = message
        builder.apply {
            writeOpcode(WIDGET_WINDOW)
            writeShort(id, Modifier.ADD, Endian.LITTLE)
            writeByte(type, Modifier.SUBTRACT)
        }
    }

}