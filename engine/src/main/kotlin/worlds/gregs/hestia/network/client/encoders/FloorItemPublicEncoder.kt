package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.FLOOR_ITEM_PUBLIC
import worlds.gregs.hestia.network.client.encoders.messages.FloorItemPublic

class FloorItemPublicEncoder : MessageEncoder<FloorItemPublic>() {

    override fun encode(builder: PacketBuilder, message: FloorItemPublic) {
        val (owner, position, id, amount) = message
        builder.apply {
            writeOpcode(FLOOR_ITEM_PUBLIC)
            writeShort(owner, type = Modifier.ADD)
            writeByte(position, type = Modifier.ADD)
            writeShort(id, order = Endian.LITTLE)
            writeShort(amount)
        }
    }

}