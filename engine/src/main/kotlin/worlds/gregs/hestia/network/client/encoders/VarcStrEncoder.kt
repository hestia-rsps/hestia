package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.network.client.encoders.messages.VarcStr

class VarcStrEncoder : MessageEncoder<VarcStr>() {

    override fun encode(builder: PacketBuilder, message: VarcStr) {
        val (id, value) = message
        builder.apply {
            writeOpcode(54, Packet.Type.VAR_BYTE)
            writeShort(id, Modifier.ADD, Endian.LITTLE)
            writeString(value)
        }
    }

}