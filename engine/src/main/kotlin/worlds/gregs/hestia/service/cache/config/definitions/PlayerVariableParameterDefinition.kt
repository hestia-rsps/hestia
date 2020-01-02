package worlds.gregs.hestia.service.cache.config.definitions

import world.gregs.hestia.core.network.codec.packet.Packet
import worlds.gregs.hestia.service.cache.Definition

class PlayerVariableParameterDefinition : Definition {

    var id = 0

    override fun readValues(opcode: Int, packet: Packet, member: Boolean) {
        if (opcode == 5) {
            id = packet.readShort()
        }
    }
}