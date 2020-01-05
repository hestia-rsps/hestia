package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.network.client.encoders.messages.WidgetVisibility

class WidgetVisibilityEncoder : MessageEncoder<WidgetVisibility>() {

    override fun encode(builder: PacketBuilder, message: WidgetVisibility) {
        val (id, widget, visible) = message
        builder.apply {
            writeOpcode(117)
            writeInt(id shl 16 or widget, order = Endian.MIDDLE)
            writeByte(visible, Modifier.ADD)
        }
    }

}