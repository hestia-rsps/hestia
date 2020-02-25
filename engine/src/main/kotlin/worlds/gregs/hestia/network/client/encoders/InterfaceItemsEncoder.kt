package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.io.Endian
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.INTERFACE_ITEMS
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceItems

class InterfaceItemsEncoder : MessageEncoder<InterfaceItems>() {

    override fun encode(builder: PacketBuilder, message: InterfaceItems) {
        val (key, items, negativeKey) = message
        builder.apply {
            writeOpcode(INTERFACE_ITEMS, Packet.Type.VAR_SHORT)
            writeShort(if (negativeKey) key else key)
            writeByte(negativeKey)
            writeShort(items.size)
            for ((item, amount) in items) {
                writeByte(if (amount >= 255) 255 else amount)
                if (amount >= 255) {
                    writeInt(amount)
                }
                writeShort(item + 1, order = Endian.LITTLE)
            }
        }
    }

}