package worlds.gregs.hestia.service.cache.config.definitions

import world.gregs.hestia.core.network.codec.packet.Packet
import worlds.gregs.hestia.service.cache.Definition
import java.util.*

class WorldMapInfoDefinition : Definition {
    var anInt1042 = 0
    var anInt1044 = -1
    var aString1045: String? = null
    var aBoolean1047: Boolean = true
    var anInt1048: Int = -1
    var anIntArray1049: IntArray? = null
    var anInt1050 = -1
    var params: HashMap<Long?, Any?>? = null
    var anInt1054: Int = -1
    var anInt1056: Int = -1
    var aByteArray1057: ByteArray? = null
    var anInt1058 = 0
    var anInt1062: Int = -1
    var aBoolean1063: Boolean = true
    var aBoolean1064: Boolean = true
    var aStringArray1065: Array<String?> = arrayOfNulls(5)
    var anIntArray1066: IntArray? = null
    var anInt1067: Int = -1
    var anInt1069: Int = -1
    var anInt1071 = 0
    var anInt1072 = 0
    var anInt1074: Int = -1
    var anInt1077 = 0
    var anInt1078 = 0
    var aBoolean1079: Boolean = false
    var anInt1080: Int = -1
    var anInt1081 = 0
    var anInt1084 = 0
    var aString1085: String? = null
    var anInt1087 = 0
    var anInt1090: Int = 0
    var anInt1091: Int = -1
    var anInt1092 = 0
    var anInt1093: Int = -1
    var id: Int = 0

    var anInt1068 = 2147483647
    var anInt1089 = -2147483648
    var anInt1051 = 2147483647
    var anInt1060 = -2147483648

    fun changeValues() {
        if (anIntArray1049 != null) {
            var i = 0
            while (anIntArray1049!!.size > i) {
                if (anIntArray1049!![i] >= anInt1068) {
                    if (anInt1089 < anIntArray1049!![i]) {
                        anInt1089 = anIntArray1049!![i]
                    }
                } else {
                    anInt1068 = anIntArray1049!![i]
                }
                if (anInt1051 > anIntArray1049!![i + 1]) {
                    anInt1051 = anIntArray1049!![1 + i]
                } else if (anIntArray1049!![1 + i] > anInt1060) {
                    anInt1060 = anIntArray1049!![i + 1]
                }
                i += 2
            }
        }
    }

    override fun readValues(opcode: Int, packet: Packet, member: Boolean) {
        when (opcode) {
            1 -> anInt1062 = packet.readShort()
            2 -> anInt1056 = packet.readShort()
            3 -> aString1085 = packet.readString()
            4 -> anInt1058 = packet.readMedium()
            5 -> anInt1054 = packet.readMedium()
            6 -> anInt1090 = packet.readUnsignedByte()
            7 -> {
                val i_6_ = packet.readUnsignedByte()
                if (0x1 and i_6_ == 0) {
                    aBoolean1047 = false
                }
                if (i_6_ and 0x2 == 2) {
                    aBoolean1079 = true
                }
            }
            8 -> aBoolean1063 = packet.readUnsignedByte() == 1
            9 -> {
                anInt1069 = packet.readShort()
                if (anInt1069 == 65535) {
                    anInt1069 = -1
                }
                anInt1091 = packet.readShort()
                if (anInt1091 == 65535) {
                    anInt1091 = -1
                }
                anInt1087 = packet.readInt()
                anInt1042 = packet.readInt()
            }
            in 10..14 -> aStringArray1065[opcode + -10] = packet.readString()
            15 -> {
                val length = packet.readUnsignedByte()
                anIntArray1049 = IntArray(length * 2)
                repeat(length * 2) { count ->
                    anIntArray1049!![count] = packet.readUnsignedShort()
                }
                anInt1084 = packet.readInt()
                val size = packet.readUnsignedByte()
                anIntArray1066 = IntArray(size)
                repeat(size) { count ->
                    anIntArray1066!![count] = packet.readInt()
                }
                aByteArray1057 = ByteArray(length)
                repeat(length) { count ->
                    aByteArray1057!![count] = packet.readByte().toByte()
                }
            }
            16 -> aBoolean1064 = false
            17 -> aString1045 = packet.readString()
            18 -> anInt1093 = packet.readShort()
            19 -> anInt1067 = packet.readShort()
            20 -> {
                anInt1048 = packet.readShort()
                if (anInt1048 == 65535) {
                    anInt1048 = -1
                }
                anInt1044 = packet.readShort()
                if (anInt1044 == 65535) {
                    anInt1044 = -1
                }
                anInt1078 = packet.readInt()
                anInt1072 = packet.readInt()
            }
            21 -> anInt1081 = packet.readInt()
            22 -> anInt1077 = packet.readInt()
            23 -> {
                anInt1074 = packet.readUnsignedByte()
                anInt1050 = packet.readUnsignedByte()
                anInt1080 = packet.readUnsignedByte()
            }
            24 -> {
                anInt1071 = packet.readUnsignedShort()
                anInt1092 = packet.readUnsignedShort()
            }
            249 -> {
                val length = packet.readUnsignedByte()
                if (params == null) {
                    val initialCapacity: Int = calculateCapacity(length)
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
}