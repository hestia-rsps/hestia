package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.WIDGET_CLOSE
import worlds.gregs.hestia.network.client.encoders.messages.WidgetClose

class WidgetCloseEncoder : MessageEncoder<WidgetClose>() {

    override fun encode(builder: PacketBuilder, message: WidgetClose) {
        val (id, widget) = message
        builder.apply {
            writeOpcode(WIDGET_CLOSE)
            writeInt(id shl 16 or widget, order = Endian.LITTLE)
        }
    }

}