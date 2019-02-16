package worlds.gregs.hestia.network.game.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.WIDGET_ITEM
import worlds.gregs.hestia.network.game.encoders.messages.WidgetItem

class WidgetItemEncoder : MessageEncoder<WidgetItem>() {

    override fun encode(builder: PacketBuilder, message: WidgetItem) {
        val (id, component, item, amount) = message
        builder.apply {
            writeOpcode(WIDGET_ITEM)
            writeShort(item, order = Endian.LITTLE)
            writeInt(amount)
            writeInt(id shl 16 or component, Modifier.INVERSE, Endian.MIDDLE)
        }
    }

}