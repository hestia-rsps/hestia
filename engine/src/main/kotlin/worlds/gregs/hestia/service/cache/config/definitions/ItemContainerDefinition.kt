package worlds.gregs.hestia.service.cache.config.definitions

import world.gregs.hestia.core.network.codec.packet.Packet
import worlds.gregs.hestia.service.cache.Definition

class ItemContainerDefinition : Definition {

    var length = 0
    var ids: IntArray? = null
    var amounts: IntArray? = null

    override fun readValues(opcode: Int, packet: Packet, member: Boolean) {
        when(opcode) {
            2 -> length = packet.readUnsignedShort()
            4 -> {
                val size = packet.readUnsignedByte()
                ids = IntArray(size)
                amounts = IntArray(size)
                repeat(size) { i ->
                    ids!![i] = packet.readUnsignedShort()
                    amounts!![i] = packet.readUnsignedShort()
                }
            }
        }
    }

}