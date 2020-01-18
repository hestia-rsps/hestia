package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.INTERFACE_ANIMATION
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceAnimation

class InterfaceAnimationEncoder : MessageEncoder<InterfaceAnimation>() {

    override fun encode(builder: PacketBuilder, message: InterfaceAnimation) {
        val (id, component, animation) = message
        builder.apply {
            writeOpcode(INTERFACE_ANIMATION)
            writeShort(animation, Modifier.ADD, Endian.LITTLE)
            writeInt(id shl 16 or component, order = Endian.MIDDLE)
        }
    }

}