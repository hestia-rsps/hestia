package worlds.gregs.hestia.network.game.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.WIDGET_ITEMS
import worlds.gregs.hestia.network.game.encoders.messages.WidgetItems

class WidgetItemsEncoder : MessageEncoder<WidgetItems>() {

    override fun encode(builder: PacketBuilder, message: WidgetItems) {
        val (key, items, negativeKey) = message
        builder.apply {
            writeOpcode(WIDGET_ITEMS, Packet.Type.VAR_SHORT)
            writeShort(if (negativeKey) key else key)
            writeByte(negativeKey)
            writeShort(items.size)
            for (item in items) {
                val amount = 1//TODO item interface
                writeByte(if (amount >= 255) 255 else amount)
                if (amount >= 255) {
                    writeInt(amount)
                }
                writeShort(item + 1, order = Endian.LITTLE)
            }
        }
    }

}