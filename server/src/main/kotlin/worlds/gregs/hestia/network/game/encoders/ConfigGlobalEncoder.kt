package worlds.gregs.hestia.network.game.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.GLOBAL_CONFIG
import world.gregs.hestia.core.network.protocol.ClientOpcodes.GLOBAL_CONFIG_LARGE
import worlds.gregs.hestia.network.game.encoders.messages.ConfigGlobal

class ConfigGlobalEncoder : MessageEncoder<ConfigGlobal>() {

    override fun encode(builder: PacketBuilder, message: ConfigGlobal) {
        val (id, value, large) = message
        builder.apply {
            writeOpcode(if(large) GLOBAL_CONFIG_LARGE else GLOBAL_CONFIG)
            if(large) {
                writeShort(id, order = Endian.LITTLE)
                writeInt(value)
            } else {
                writeShort(id, Modifier.ADD, Endian.LITTLE)
                writeByte(value, Modifier.SUBTRACT)
            }
        }
    }

}