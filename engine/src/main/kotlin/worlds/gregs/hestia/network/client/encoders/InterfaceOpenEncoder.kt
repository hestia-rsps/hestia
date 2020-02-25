package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.io.Endian
import world.gregs.hestia.io.Modifier
import world.gregs.hestia.core.network.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.INTERFACE_OPEN
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceOpenMessage

class InterfaceOpenEncoder : MessageEncoder<InterfaceOpenMessage>() {

    override fun encode(builder: PacketBuilder, message: InterfaceOpenMessage) {
        val (permanent, parent, component, id) = message
        builder.apply {
            writeOpcode(INTERFACE_OPEN)
            writeShort(id, Modifier.ADD, Endian.LITTLE)
            writeInt(parent shl 16 or component, order = Endian.LITTLE)
            writeByte(permanent)
        }
    }

}