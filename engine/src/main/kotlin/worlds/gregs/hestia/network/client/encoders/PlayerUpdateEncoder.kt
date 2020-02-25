package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.PLAYER_UPDATING
import worlds.gregs.hestia.network.client.encoders.messages.PlayerUpdate

class PlayerUpdateEncoder : MessageEncoder<PlayerUpdate>() {

    override fun encode(builder: PacketBuilder, message: PlayerUpdate) {
        builder.apply {
            writeOpcode(PLAYER_UPDATING, Packet.Type.VAR_SHORT)

            message.stages.forEach {
                it.encode(this)
                it.free()
            }

            message.blocks.forEach {
                it.encode(this)
            }
        }
        message.free()
    }

}