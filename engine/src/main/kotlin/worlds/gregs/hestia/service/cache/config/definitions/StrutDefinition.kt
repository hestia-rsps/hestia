package worlds.gregs.hestia.service.cache.config.definitions

import world.gregs.hestia.core.network.codec.packet.Packet
import worlds.gregs.hestia.service.cache.Definition

class StrutDefinition : Definition {

    var params: HashMap<Long, Any>? = null

    override fun readValues(opcode: Int, packet: Packet, member: Boolean) {
        if (opcode == 249) {
            val length = packet.readUnsignedByte()
            if (params == null) {
                val initialCapacity = calculateCapacity(length)
                params = HashMap(initialCapacity)
            }
            repeat(length) {
                val isString = packet.readUnsignedBoolean()
                val id = packet.readMedium()
                params!![id.toLong()] = if (isString) {
                    packet.readString()
                } else {
                    packet.readInt()
                }
            }
        }
    }
}