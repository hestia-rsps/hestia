package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.io.Modifier
import world.gregs.hestia.core.network.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.FLOOR_ITEM_PRIVATE
import worlds.gregs.hestia.network.client.encoders.messages.FloorItemPrivate

class FloorItemPrivateEncoder : MessageEncoder<FloorItemPrivate>() {

    override fun encode(builder: PacketBuilder, message: FloorItemPrivate) {
        val (position, id, amount) = message
        builder.apply {
            writeOpcode(FLOOR_ITEM_PRIVATE)
            writeByte(position, type = Modifier.INVERSE)
            writeShort(id, type = Modifier.ADD)
            writeShort(amount)
        }
    }

}