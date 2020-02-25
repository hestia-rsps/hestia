package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.FLOOR_ITEM_REMOVE
import worlds.gregs.hestia.network.client.encoders.messages.FloorItemRemove

class FloorItemRemoveEncoder : MessageEncoder<FloorItemRemove>() {

    override fun encode(builder: PacketBuilder, message: FloorItemRemove) {
        val (id, position) = message
        builder.apply {
            writeOpcode(FLOOR_ITEM_REMOVE)
            writeShort(id)
            writeByte(position)
        }
    }

}