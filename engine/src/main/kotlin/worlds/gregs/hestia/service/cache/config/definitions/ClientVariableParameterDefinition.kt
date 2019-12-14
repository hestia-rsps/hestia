package worlds.gregs.hestia.service.cache.config.definitions

import world.gregs.hestia.core.network.codec.packet.Packet
import worlds.gregs.hestia.service.cache.Definition

class ClientVariableParameterDefinition : Definition {
    var anInt3208 = 1
    var aChar3210 = 0.toChar()

    override fun readValues(opcode: Int, packet: Packet, member: Boolean) {
        if (opcode == 1) {
            aChar3210 = byteToChar(packet.readByte().toByte())
        } else if (opcode == 2) {
            anInt3208 = 0
        }
    }
}