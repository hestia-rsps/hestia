package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.FILE_CONFIG
import world.gregs.hestia.core.network.protocol.ClientOpcodes.FILE_CONFIG_LARGE
import worlds.gregs.hestia.network.client.encoders.messages.ConfigFile

class ConfigFileEncoder : MessageEncoder<ConfigFile>() {

    override fun encode(builder: PacketBuilder, message: ConfigFile) {
        val (id, value, large) = message
        builder.apply {
            writeOpcode(if(large) FILE_CONFIG_LARGE else FILE_CONFIG)
            if(large) {
                writeInt(value)
                writeShort(id)
            } else {
                writeByte(value, Modifier.SUBTRACT)
                writeShort(value, Modifier.ADD)
            }
        }
    }

}