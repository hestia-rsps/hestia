package worlds.gregs.hestia.service.cache.definition.definitions

import world.gregs.hestia.core.network.codec.packet.Packet
import worlds.gregs.hestia.service.cache.Definition

/**
 * Equipment Slots Definition
 */
class BodyDefinition : Definition {
    var anIntArray4501: IntArray? = null
    var anInt4504 = -1
    var anInt4506 = -1
    var anIntArray4507: IntArray? = null
    var disabledSlots = IntArray(0)

    override fun readValues(opcode: Int, packet: Packet, member: Boolean) {
        when (opcode) {
            1 -> {
                val length = packet.readUnsignedByte()
                disabledSlots = IntArray(length)
                repeat(disabledSlots.size) { count ->
                    disabledSlots[count] = packet.readUnsignedByte()
                }
            }
            3 -> anInt4506 = packet.readUnsignedByte()
            4 -> anInt4504 = packet.readUnsignedByte()
            5 -> {
                anIntArray4501 = IntArray(packet.readUnsignedByte())
                repeat(anIntArray4501!!.size) { count ->
                    anIntArray4501!![count] = packet.readUnsignedByte()
                }
            }
            6 -> {
                anIntArray4507 = IntArray(packet.readUnsignedByte())
                repeat(anIntArray4507!!.size) { count ->
                    anIntArray4507!![count] = packet.readUnsignedByte()
                }
            }
        }
    }
}