package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.INTERFACE_PLAYER_HEAD
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceHeadPlayer

class InterfaceHeadPlayerEncoder : MessageEncoder<InterfaceHeadPlayer>() {

    override fun encode(builder: PacketBuilder, message: InterfaceHeadPlayer) {
        val (id, component) = message
        builder.apply {
            writeOpcode(INTERFACE_PLAYER_HEAD)
            writeInt(id shl 16 or component, order = Endian.LITTLE)
        }
    }

}