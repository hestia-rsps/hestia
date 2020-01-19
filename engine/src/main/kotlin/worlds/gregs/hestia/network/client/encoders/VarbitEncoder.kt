package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.CLIENT_VARBIT
import world.gregs.hestia.core.network.protocol.ClientOpcodes.CLIENT_VARBIT_LARGE
import worlds.gregs.hestia.network.client.encoders.messages.Varbit

class VarbitEncoder : MessageEncoder<Varbit>() {

    override fun encode(builder: PacketBuilder, message: Varbit) {
        val (id, value, large) = message
        builder.apply {
            writeOpcode(if(large) CLIENT_VARBIT_LARGE else CLIENT_VARBIT)
            if(large) {
                writeInt(value)
                writeShort(id)
            } else {
                writeByte(value, Modifier.SUBTRACT)
                writeShort(id, Modifier.ADD)
            }
        }
    }

}