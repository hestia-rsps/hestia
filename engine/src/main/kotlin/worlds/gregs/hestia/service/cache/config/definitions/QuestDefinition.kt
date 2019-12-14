package worlds.gregs.hestia.service.cache.config.definitions

import world.gregs.hestia.core.network.codec.packet.Packet
import worlds.gregs.hestia.service.cache.Definition

class QuestDefinition : Definition {
    var anInt2188 = -1
    var anIntArray2190: IntArray? = null
    var anIntArray2191: IntArray? = null
    var anIntArrayArray2193: Array<IntArray>? = null
    var params: HashMap<Long, Any>? = null
    var anIntArray2195: IntArray? = null
    var aStringArray2198: Array<String?>? = null
    var anIntArray2199: IntArray? = null
    var anIntArray2200: IntArray? = null
    var aStringArray2201: Array<String?>? = null
    var aString2202: String? = null
    var anIntArray2204: IntArray? = null
    var anIntArray2207: IntArray? = null
    var anIntArrayArray2208: Array<IntArray>? = null
    var anIntArray2209: IntArray? = null
    var anIntArrayArray2210: Array<IntArray>? = null
    var aString2211: String? = null

    fun changeValues() {
        if (aString2202 == null) {
            aString2202 = aString2211
        }
    }

    private fun Packet.readPrefixedString(): String {
        val head: Byte = buffer.readByte()
        check(head.toInt() == 0) { "Bad version number in gjstr2" }
        val i: Int = buffer.readerIndex()
        while (buffer.readByte() != 0.toByte()) { /* empty */
        }
        val start: Int = buffer.readerIndex() + -i + -1
        return if (start == 0) {
            ""
        } else {
            val cs = CharArray(start)
            var index = 0
            repeat(start) { count ->
                cs[index++] = byteToChar(buffer.getByte(count + i))
            }
            return String(cs, 0, index)
        }
    }

    override fun readValues(opcode: Int, packet: Packet, member: Boolean) {
        when (opcode) {
            1 -> aString2211 = packet.readPrefixedString()
            2 -> aString2202 = packet.readPrefixedString()
            3 -> {
                val length = packet.readUnsignedByte()
                anIntArrayArray2208 = Array(length) { IntArray(3) }
                repeat(length) { count ->
                    anIntArrayArray2208!![count][0] = packet.readShort()
                    anIntArrayArray2208!![count][1] = packet.readInt()
                    anIntArrayArray2208!![count][2] = packet.readInt()
                }
            }
            4 -> {
                val length = packet.readUnsignedByte()
                anIntArrayArray2193 = Array(length) { IntArray(3) }
                repeat(length) { count ->
                    anIntArrayArray2193!![count][0] = packet.readShort()
                    anIntArrayArray2193!![count][1] = packet.readInt()
                    anIntArrayArray2193!![count][2] = packet.readInt()
                }
            }
            5 -> packet.readShort()
            6 -> packet.readUnsignedByte()
            7 -> packet.readUnsignedByte()
            9 -> packet.readUnsignedByte()
            10 -> {
                val length = packet.readUnsignedByte()
                anIntArray2209 = IntArray(length)
                repeat(length) { count ->
                    anIntArray2209!![count] = packet.readInt()
                }
            }
            12 -> packet.readInt()
            13 -> {
                val length = packet.readUnsignedByte()
                anIntArray2207 = IntArray(length)
                repeat(length) { count ->
                    anIntArray2207!![count] = packet.readShort()
                }
            }
            14 -> {
                val length = packet.readUnsignedByte()
                anIntArrayArray2210 = Array(length) { IntArray(2) }
                repeat(length) { count ->
                    anIntArrayArray2210!![count][0] = packet.readUnsignedByte()
                    anIntArrayArray2210!![count][1] = packet.readUnsignedByte()
                }
            }
            15 -> packet.readShort()
            17 -> anInt2188 = packet.readShort()
            18 -> {
                val length = packet.readUnsignedByte()
                aStringArray2201 = arrayOfNulls(length)
                anIntArray2200 = IntArray(length)
                anIntArray2199 = IntArray(length)
                anIntArray2191 = IntArray(length)
                repeat(length) { count ->
                    anIntArray2200!![count] = packet.readInt()
                    anIntArray2191!![count] = packet.readInt()
                    anIntArray2199!![count] = packet.readInt()
                    aStringArray2201!![count] = packet.readString()
                }
            }
            19 -> {
                val length = packet.readUnsignedByte()
                anIntArray2204 = IntArray(length)
                aStringArray2198 = arrayOfNulls(length)
                anIntArray2195 = IntArray(length)
                anIntArray2190 = IntArray(length)
                repeat(length) { count ->
                    anIntArray2204!![count] = packet.readInt()
                    anIntArray2195!![count] = packet.readInt()
                    anIntArray2190!![count] = packet.readInt()
                    aStringArray2198!![count] = packet.readString()
                }
            }
            249 -> {
                val length = packet.readUnsignedByte()
                if (params == null) {
                    val capacity: Int = calculateCapacity(length)
                    params = HashMap(capacity)
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
}