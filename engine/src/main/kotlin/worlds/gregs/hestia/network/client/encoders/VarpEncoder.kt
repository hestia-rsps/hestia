package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.io.Endian
import world.gregs.hestia.io.Modifier
import world.gregs.hestia.core.network.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.CLIENT_VARP
import world.gregs.hestia.core.network.protocol.ClientOpcodes.CLIENT_VARP_LARGE
import worlds.gregs.hestia.network.client.encoders.messages.Varp

class VarpEncoder : MessageEncoder<Varp>() {

    override fun encode(builder: PacketBuilder, message: Varp) {
        val (id, value, large) = message
        builder.apply {
            writeOpcode(if(large) CLIENT_VARP_LARGE else CLIENT_VARP)
            if(large) {
                writeInt(value, Modifier.INVERSE, Endian.MIDDLE)
                writeShort(id, Modifier.ADD)
            } else {
                writeShort(id)
                writeByte(value, Modifier.ADD)
            }
        }
    }

}