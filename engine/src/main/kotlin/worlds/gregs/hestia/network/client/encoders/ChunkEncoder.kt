package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.UPDATE_CHUNK
import worlds.gregs.hestia.network.client.encoders.messages.ChunkSend

class ChunkEncoder : MessageEncoder<ChunkSend>() {

    override fun encode(builder: PacketBuilder, message: ChunkSend) {
        val (x, y, plane) = message
        builder.apply {
            writeOpcode(UPDATE_CHUNK)
            writeByte(x, Modifier.ADD)
            writeByte(y)
            writeByte(plane, type = Modifier.SUBTRACT)
        }
    }

}