package worlds.gregs.hestia.network.world.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.WorldOpcodes.CLIENT_PACKET
import world.gregs.hestia.core.network.protocol.messages.ClientPacket

class ClientPacketEncoder : MessageEncoder<ClientPacket>() {

    override fun encode(builder: PacketBuilder, message: ClientPacket) {
        val (entity, packet, handshake) = message
        builder.apply {
            writeOpcode(CLIENT_PACKET, Packet.Type.VAR_SHORT)
            writeInt(entity)
            writeByte(handshake)
            writeByte(packet.opcode)
            writeBytes(packet.buffer)
        }
    }

}