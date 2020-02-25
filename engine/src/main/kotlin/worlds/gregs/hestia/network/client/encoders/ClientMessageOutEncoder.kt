package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.packet.PacketBuilder
import worlds.gregs.hestia.network.client.encoders.messages.ClientMessageOut

class ClientMessageOutEncoder : MessageEncoder<ClientMessageOut>() {

    override fun encode(builder: PacketBuilder, message: ClientMessageOut) {
        val (opcode, packet) = message
        builder.apply {
            writeOpcode(opcode)
            writeBytes(packet.buffer)
        }
    }

}