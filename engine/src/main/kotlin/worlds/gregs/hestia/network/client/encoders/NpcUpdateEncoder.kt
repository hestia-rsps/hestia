package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.NPC_UPDATING
import worlds.gregs.hestia.network.client.encoders.messages.NpcUpdate

class NpcUpdateEncoder : MessageEncoder<NpcUpdate>() {

    override fun encode(builder: PacketBuilder, message: NpcUpdate) {
        builder.apply {
            writeOpcode(NPC_UPDATING, Packet.Type.VAR_SHORT)

            message.stages.forEach {
                it.encode(this)
            }

            message.blocks.forEach {
                it.encode(this)
            }
        }
        message.free()
    }

}