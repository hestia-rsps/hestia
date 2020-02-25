package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.io.Endian
import world.gregs.hestia.io.Modifier
import world.gregs.hestia.core.network.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.INTERFACE_ITEM
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceItem

class InterfaceItemEncoder : MessageEncoder<InterfaceItem>() {

    override fun encode(builder: PacketBuilder, message: InterfaceItem) {
        val (id, component, item, amount) = message
        builder.apply {
            writeOpcode(INTERFACE_ITEM)
            writeShort(item, order = Endian.LITTLE)
            writeInt(amount)
            writeInt(id shl 16 or component, Modifier.INVERSE, Endian.MIDDLE)
        }
    }

}