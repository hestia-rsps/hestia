package worlds.gregs.hestia.service.cache.definition.definitions

import world.gregs.hestia.core.network.codec.packet.Packet
import worlds.gregs.hestia.service.cache.Definition

class VarBitDefinition : Definition {
    var leastSignificantBit = 0
    var mostSignificantBit = 0
    var index = 0

    override fun readValues(opcode: Int, packet: Packet, member: Boolean) {
        if (opcode == 1) {
            index = packet.readShort()
            leastSignificantBit = packet.readUnsignedByte()
            mostSignificantBit = packet.readUnsignedByte()
        }
    }
}