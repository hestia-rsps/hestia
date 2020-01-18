package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.INTERFACE_OPEN
import worlds.gregs.hestia.network.client.encoders.messages.WindowOpen

class WindowOpenEncoder : MessageEncoder<WindowOpen>() {

    override fun encode(builder: PacketBuilder, message: WindowOpen) {
        val (permanent, window, widget, id) = message
        builder.apply {
            writeOpcode(INTERFACE_OPEN)
            writeShort(id, Modifier.ADD, Endian.LITTLE)
            writeInt(window shl 16 or widget, order = Endian.LITTLE)
            writeByte(permanent)
        }
    }

}