package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.io.Endian
import world.gregs.hestia.io.Modifier
import world.gregs.hestia.core.network.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes
import world.gregs.hestia.core.network.protocol.ClientOpcodes.INTERFACE_COMPONENT_VISIBILITY
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceVisibility

class InterfaceVisibilityEncoder : MessageEncoder<InterfaceVisibility>() {

    override fun encode(builder: PacketBuilder, message: InterfaceVisibility) {
        val (id, component, visible) = message
        builder.apply {
            writeOpcode(INTERFACE_COMPONENT_VISIBILITY)
            writeInt(id shl 16 or component, order = Endian.MIDDLE)
            writeByte(visible, Modifier.ADD)
        }
    }

}