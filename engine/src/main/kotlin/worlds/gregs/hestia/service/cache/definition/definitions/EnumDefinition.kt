package worlds.gregs.hestia.service.cache.definition.definitions

import world.gregs.hestia.core.network.codec.packet.Packet
import worlds.gregs.hestia.service.cache.Definition

class EnumDefinition : Definition {
    var defaultString = "null"
    var defaultInt = 0
    var length = 0
    var valueType = 0.toChar()
    var map: HashMap<Int, Any>? = null
    var keyType = 0.toChar()

    fun getKey(value: Any): Int {
        return map?.filter { it.value == value }?.toList()?.firstOrNull()?.first ?: -1
    }

    fun getInt(id: Int): Int {
        return map?.get(id) as? Int ?: defaultInt
    }

    fun getString(id: Int): String {
        return map?.get(id) as? String ?: defaultString
    }

    override fun readValues(opcode: Int, packet: Packet, member: Boolean) {
        when (opcode) {
            1 -> keyType = byteToChar(packet.readByte().toByte())
            2 -> valueType = byteToChar(packet.readByte().toByte())
            3 -> defaultString = packet.readString()
            4 -> defaultInt = packet.readInt()
            5, 6 -> {
                length = packet.readShort()
                val hashtable = HashMap<Int, Any>(calculateCapacity(length))
                repeat(length) {
                    val id = packet.readInt()
                    hashtable[id] = if (opcode == 5) {
                        packet.readString()
                    } else {
                        packet.readInt()
                    }
                }
                map = hashtable
            }
            7 -> {
                val size = packet.readShort()
                length = packet.readShort()
                val strings = HashMap<Int, Any>(size)
                repeat(length) {
                    val index = packet.readShort()
                    strings[index] = packet.readString()
                }
                map = strings
            }
            8 -> {
                val size = packet.readShort()
                length = packet.readShort()
                val integers = HashMap<Int, Any>(size)
                repeat(length) {
                    val index = packet.readShort()
                    integers[index] = packet.readInt()
                }
                map = integers
            }
        }
    }
}