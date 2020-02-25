package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.io.Endian
import world.gregs.hestia.io.Modifier
import world.gregs.hestia.core.network.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.INTERFACE_WINDOW
import worlds.gregs.hestia.network.client.encoders.messages.WindowUpdate

class WindowUpdateEncoder : MessageEncoder<WindowUpdate>() {

    override fun encode(builder: PacketBuilder, message: WindowUpdate) {
        val (id, type) = message
        builder.apply {
            writeOpcode(INTERFACE_WINDOW)
            writeShort(id, Modifier.ADD, Endian.LITTLE)
            writeByte(type, Modifier.SUBTRACT)
        }
    }

}