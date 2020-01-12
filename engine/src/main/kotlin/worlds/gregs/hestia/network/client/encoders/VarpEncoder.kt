package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.CONFIG
import world.gregs.hestia.core.network.protocol.ClientOpcodes.CONFIG_LARGE
import worlds.gregs.hestia.network.client.encoders.messages.Varp

class VarpEncoder : MessageEncoder<Varp>() {

    override fun encode(builder: PacketBuilder, message: Varp) {
        val (id, value, large) = message
        builder.apply {
            writeOpcode(if(large) CONFIG_LARGE else CONFIG)
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