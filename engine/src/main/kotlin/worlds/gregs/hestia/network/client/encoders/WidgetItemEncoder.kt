package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.INTERFACE_ITEM
import worlds.gregs.hestia.network.client.encoders.messages.WidgetItem

class WidgetItemEncoder : MessageEncoder<WidgetItem>() {

    override fun encode(builder: PacketBuilder, message: WidgetItem) {
        val (id, widget, item, amount) = message
        builder.apply {
            writeOpcode(INTERFACE_ITEM)
            writeShort(item, order = Endian.LITTLE)
            writeInt(amount)
            writeInt(id shl 16 or widget, Modifier.INVERSE, Endian.MIDDLE)
        }
    }

}