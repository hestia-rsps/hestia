package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.io.Endian
import world.gregs.hestia.io.Modifier
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes
import world.gregs.hestia.core.network.protocol.ClientOpcodes.CLIENT_VARC_STR
import worlds.gregs.hestia.network.client.encoders.messages.VarcStr

class VarcStrEncoder : MessageEncoder<VarcStr>() {

    override fun encode(builder: PacketBuilder, message: VarcStr) {
        val (id, value) = message
        builder.apply {
            writeOpcode(CLIENT_VARC_STR, Packet.Type.VAR_BYTE)
            writeShort(id, Modifier.ADD, Endian.LITTLE)
            writeString(value)
        }
    }

}