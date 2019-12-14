package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.network.client.encoders.messages.MobUpdate

class MobUpdateEncoder : MessageEncoder<MobUpdate>() {

    override fun encode(builder: PacketBuilder, message: MobUpdate) {
        builder.apply {
            writeOpcode(6, Packet.Type.VAR_SHORT)

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