package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.network.client.encoders.messages.PlayerUpdate

class PlayerUpdateEncoder : MessageEncoder<PlayerUpdate>() {

    override fun encode(builder: PacketBuilder, message: PlayerUpdate) {
        builder.apply {
            writeOpcode(69, Packet.Type.VAR_SHORT)

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