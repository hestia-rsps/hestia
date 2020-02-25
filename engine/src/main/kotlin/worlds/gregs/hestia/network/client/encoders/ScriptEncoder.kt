package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.SCRIPT
import worlds.gregs.hestia.network.client.encoders.messages.Script

class ScriptEncoder : MessageEncoder<Script>() {

    override fun encode(builder: PacketBuilder, message: Script) {
        val (id, params) = message
        builder.apply {
            writeOpcode(SCRIPT, Packet.Type.VAR_SHORT)
            var parameterTypes = ""
            for (count in params.indices.reversed()) {
                parameterTypes += if (params[count] is String) {
                    "s" // string
                } else {
                    "i" // integer
                }
            }
            writeString(parameterTypes)
            var index = 0
            for (count in parameterTypes.length - 1 downTo 0) {
                if (parameterTypes[count] == 's') {
                    writeString(params[index++] as String)
                } else {
                    writeInt(params[index++] as Int)
                }
            }
            writeInt(id)
        }
    }

}