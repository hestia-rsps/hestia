package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.io.Modifier
import world.gregs.hestia.core.network.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.FLOOR_ITEM_CLEAR
import worlds.gregs.hestia.network.client.encoders.messages.ChunkClear

class FloorItemClearEncoder : MessageEncoder<ChunkClear>() {

    override fun encode(builder: PacketBuilder, message: ChunkClear) {
        val (x, y, plane) = message
        builder.apply {
            writeOpcode(FLOOR_ITEM_CLEAR)
            writeByte(x)
            writeByte(y, Modifier.SUBTRACT)
            writeByte(plane)
        }
    }

}