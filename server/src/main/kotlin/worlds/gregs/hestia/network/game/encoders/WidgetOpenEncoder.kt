package worlds.gregs.hestia.network.game.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.WIDGET_OPEN
import worlds.gregs.hestia.network.game.encoders.messages.WidgetOpen

class WidgetOpenEncoder : MessageEncoder<WidgetOpen>() {

    override fun encode(builder: PacketBuilder, message: WidgetOpen) {
        val (permanent, window, component, id) = message
        builder.apply {
            writeOpcode(WIDGET_OPEN)
            writeShort(id, Modifier.ADD, Endian.LITTLE)
            writeInt(window shl 16 or component, order = Endian.LITTLE)
            writeByte(permanent)
        }
    }

}