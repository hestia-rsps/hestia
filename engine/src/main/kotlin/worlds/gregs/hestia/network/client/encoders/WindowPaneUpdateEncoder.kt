package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.INTERFACE_WINDOW
import worlds.gregs.hestia.network.client.encoders.messages.WindowPaneUpdate

class WindowPaneUpdateEncoder : MessageEncoder<WindowPaneUpdate>() {

    override fun encode(builder: PacketBuilder, message: WindowPaneUpdate) {
        val (id, type) = message
        builder.apply {
            writeOpcode(INTERFACE_WINDOW)
            writeShort(id, Modifier.ADD, Endian.LITTLE)
            writeByte(type, Modifier.SUBTRACT)
        }
    }

}