package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.WIDGET_ANIMATION
import worlds.gregs.hestia.network.client.encoders.messages.WindowWidgetAnimation

class WindowWidgetAnimationEncoder : MessageEncoder<WindowWidgetAnimation>() {

    override fun encode(builder: PacketBuilder, message: WindowWidgetAnimation) {
        val (id, widget, animation) = message
        builder.apply {
            writeOpcode(WIDGET_ANIMATION)
            writeShort(animation, Modifier.ADD, Endian.LITTLE)
            writeInt(id shl 16 or widget, order = Endian.MIDDLE)
        }
    }

}