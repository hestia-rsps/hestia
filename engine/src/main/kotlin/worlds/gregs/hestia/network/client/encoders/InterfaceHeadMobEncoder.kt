package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.INTERFACE_NPC_HEAD
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceHeadMob

class InterfaceHeadMobEncoder : MessageEncoder<InterfaceHeadMob>() {

    override fun encode(builder: PacketBuilder, message: InterfaceHeadMob) {
        val (id, component, mob) = message
        builder.apply {
            writeOpcode(INTERFACE_NPC_HEAD)
            writeInt(id shl 16 or component)
            writeShort(mob, order = Endian.LITTLE)
        }
    }

}