package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.FLOOR_ITEM_UPDATE
import worlds.gregs.hestia.network.client.encoders.messages.FloorItemUpdate

class FloorItemUpdateEncoder : MessageEncoder<FloorItemUpdate>() {

    override fun encode(builder: PacketBuilder, message: FloorItemUpdate) {
        val (position, id, old, new) = message
        builder.apply {
            writeOpcode(FLOOR_ITEM_UPDATE)
            writeByte(position)
            writeShort(id)
            writeShort(old)
            writeShort(new)
        }
    }

}