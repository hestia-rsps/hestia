package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.INTERFACE_CLOSE
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceClose

class InterfaceCloseEncoder : MessageEncoder<InterfaceClose>() {

    override fun encode(builder: PacketBuilder, message: InterfaceClose) {
        val (id, component) = message
        builder.apply {
            writeOpcode(INTERFACE_CLOSE)
            writeInt(id shl 16 or component, order = Endian.LITTLE)
        }
    }

}