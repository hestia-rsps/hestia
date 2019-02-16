package worlds.gregs.hestia.network.game.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.CONFIG
import world.gregs.hestia.core.network.protocol.ClientOpcodes.CONFIG_LARGE
import worlds.gregs.hestia.network.game.encoders.messages.Config

class ConfigEncoder : MessageEncoder<Config>() {

    override fun encode(builder: PacketBuilder, message: Config) {
        val (id, value, large) = message
        builder.apply {
            writeOpcode(if(large) CONFIG_LARGE else CONFIG)
            if(large) {
                writeInt(value, Modifier.INVERSE, Endian.MIDDLE)
                writeShort(id, Modifier.ADD)
            } else {
                writeShort(id)
                writeByte(value, Modifier.ADD)
            }
        }
    }

}