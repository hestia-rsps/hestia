package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.io.Endian
import world.gregs.hestia.core.network.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.INTERFACE_NPC_HEAD
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceHeadNpc

class InterfaceHeadNpcEncoder : MessageEncoder<InterfaceHeadNpc>() {

    override fun encode(builder: PacketBuilder, message: InterfaceHeadNpc) {
        val (id, component, npc) = message
        builder.apply {
            writeOpcode(INTERFACE_NPC_HEAD)
            writeInt(id shl 16 or component)
            writeShort(npc, order = Endian.LITTLE)
        }
    }

}