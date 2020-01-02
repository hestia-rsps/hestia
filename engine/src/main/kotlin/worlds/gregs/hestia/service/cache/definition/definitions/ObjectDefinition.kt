package worlds.gregs.hestia.service.cache.definition.definitions

import world.gregs.hestia.core.network.codec.packet.Packet
import worlds.gregs.hestia.service.cache.Definition

class ObjectDefinition : Definition {
    var anInt2958: Int = 0
    var aByte2960: Byte = 0
    var aBoolean2961 = false
    var anInt2962: Int = 0
    var anInt2963 = 0
    var anInt2964: Int = 0
    var modelIds: ByteArray? = null
    var offsetX: Int = 0
    var modelSizeX: Int = 128
    var supportItems: Int = -1
    var anInt2971: Int = 0
    var aBoolean2972 = true
    var modelSizeZ = 128
    var aByte2974: Byte = 0
    var anInt2975: Int = 0
    var culling: Int = -1
    lateinit var options: Array<String?>
    var anIntArray2981: IntArray? = null
    var anInt2983: Int = 0
    var configObjectIds: IntArray? = null
    var offsetY: Int = 0
    var sizeY: Int = 1
    var anInt2987: Int = -1
    var modifiedModelTextures: ShortArray? = null
    var anInt2989: Int = 0
    var aBoolean2990: Boolean = false
    var aBoolean2992: Boolean = false
    var aBoolean2993: Boolean = false
    var mapDefinitionId: Int = -1
    var anIntArray2995: IntArray? = null
    var aByteArray2996: ByteArray? = null
    var aBoolean2998: Boolean = false
    var name: String = "null"
    var animateImmediately: Boolean = true
    var aBoolean3002: Boolean = false
    var anInt3006: Int = -1
    var aBoolean3007: Boolean = false
    var anInt3008: Int = -1
    var modelSizeY: Int = 128
    var solid: Int = 2
    var offsetMultiplier: Int = 64
    var anInt3012: Int = 0
    var anInt3013: Int = -1
    var params: HashMap<Long, Any>? = null
    var anInt3015: Int = -1
    var ignoreClipOnAlternativeRoute: Boolean = false
    var varbitIndex: Int = -1
    var anInt3018: Int = 0
    var animations: IntArray? = null
    var anInt3020: Int = 256
    var anInt3023: Int = -1
    var anInt3024: Int = 255
    var modifiedColours: ShortArray? = null
    var contrast: Int = 0
    var contouredGround: Byte = 0
    var id = 0
    var modelTypes: Array<IntArray?>? = null
    var anInt3032: Int = 960
    var castsShadow: Boolean = true
    var projectileClipped: Boolean = true
    var configId: Int = -1
    var anIntArray3036: IntArray? = null
    var offsetZ: Int = 0
    var anInt3038: Int = -1
    var rotated: Boolean = false
    var plane: Int = 0
    var brightness: Int = 0
    var originalModelTextures: ShortArray? = null
    var aByte3045: Byte = 0
    var originalColours: ShortArray? = null
    var anInt3050: Int = 256
    var obstructsGround: Boolean = false
    var aByte3052: Byte = 0
    var delayShading: Boolean = false
    var sizeX: Int = 1
    var aBoolean3056: Boolean = false
    var interactive: Int = -1

    override fun readValues(opcode: Int, packet: Packet, member: Boolean) {
        when (opcode) {
            1, 5 -> {
                if (opcode == 5 && lowDetail) {
                    skip(packet)
                }
                val length = packet.readUnsignedByte()
                modelIds = ByteArray(length)
                modelTypes = arrayOfNulls(length)
                repeat(length) { count ->
                    modelIds!![count] = packet.readByte().toByte()
                    val size = packet.readUnsignedByte()
                    modelTypes!![count] = IntArray(size)
                    repeat(size) { index ->
                        modelTypes!![count]!![index] = packet.readShort()
                    }
                }
                if (opcode == 5 && !lowDetail) {
                    skip(packet)
                }
            }
            2 -> name = packet.readString()
            14 -> sizeX = packet.readUnsignedByte()
            15 -> sizeY = packet.readUnsignedByte()
            17 -> {
                projectileClipped = false
                solid = 0
            }
            18 -> projectileClipped = false
            19 -> interactive = packet.readUnsignedByte()
            21 -> contouredGround = 1.toByte()
            22 -> delayShading = true
            23 -> culling = 1
            24 -> {
                val length = packet.readShort()
                if (length != 65535) {
                    animations = intArrayOf(length)
                }
            }
            27 -> solid = 1
            28 -> offsetMultiplier = packet.readUnsignedByte() shl 2
            29 -> brightness = packet.readByte()
            in 30..34 -> options[opcode - 30] = packet.readString()
            39 -> contrast = 5 * packet.readByte()
            40 -> {
                val length = packet.readUnsignedByte()
                modifiedColours = ShortArray(length)
                originalColours = ShortArray(length)
                repeat(length) { count ->
                    originalColours!![count] = packet.readShort().toShort()
                    modifiedColours!![count] = packet.readShort().toShort()
                }
            }
            41 -> {
                val length = packet.readUnsignedByte()
                modifiedModelTextures = ShortArray(length)
                originalModelTextures = ShortArray(length)
                repeat(length) { count ->
                    originalModelTextures!![count] = packet.readShort().toShort()
                    modifiedModelTextures!![count] = packet.readShort().toShort()
                }
            }
            42 -> {
                val length = packet.readUnsignedByte()
                aByteArray2996 = ByteArray(length)
                repeat(length) { count ->
                    aByteArray2996!![count] = packet.readByte().toByte()
                }
            }
            62 -> rotated = true
            64 -> castsShadow = false
            65 -> modelSizeX = packet.readShort()
            66 -> modelSizeZ = packet.readShort()
            67 -> modelSizeY = packet.readShort()
            69 -> plane = packet.readUnsignedByte()
            70 -> offsetX = packet.readUnsignedShort() shl 2
            71 -> offsetZ = packet.readUnsignedShort() shl 2
            72 -> offsetY = packet.readUnsignedShort() shl 2
            73 -> obstructsGround = true
            74 -> ignoreClipOnAlternativeRoute = true
            75 -> supportItems = packet.readUnsignedByte()
            77, 92 -> {
                varbitIndex = packet.readShort()
                if (varbitIndex == 65535) {
                    varbitIndex = -1
                }
                configId = packet.readShort()
                if (configId == 65535) {
                    configId = -1
                }
                var last = -1
                if (opcode == 92) {
                    last = packet.readShort()
                    if (last == 65535) {
                        last = -1
                    }
                }
                val length = packet.readUnsignedByte()
                configObjectIds = IntArray(length + 2)
                var count = 0
                while (length >= count) {
                    configObjectIds!![count] = packet.readShort()
                    if (configObjectIds!![count] == 65535) {
                        configObjectIds!![count] = -1
                    }
                    count++
                }
                configObjectIds!![length + 1] = last
            }
            78 -> {
                anInt3015 = packet.readShort()
                anInt3012 = packet.readUnsignedByte()
            }
            79 -> {
                anInt2989 = packet.readShort()
                anInt2971 = packet.readShort()
                anInt3012 = packet.readUnsignedByte()
                val length = packet.readUnsignedByte()
                anIntArray3036 = IntArray(length)
                repeat(length) { count ->
                    anIntArray3036!![count] = packet.readShort()
                }
            }
            81 -> {
                contouredGround = 2.toByte()
                anInt3023 = 256 * packet.readUnsignedByte()
            }
            82 -> aBoolean2990 = true
            88 -> aBoolean2972 = false
            89 -> animateImmediately = false
            91 -> aBoolean3002 = true
            93 -> {
                contouredGround = 3.toByte()
                anInt3023 = packet.readShort()
            }
            94 -> contouredGround = 4.toByte()
            95 -> {
                contouredGround = 5.toByte()
                anInt3023 = packet.readUnsignedShort()
            }
            97 -> aBoolean3056 = true
            98 -> aBoolean2998 = true
            99 -> {
                anInt2987 = packet.readUnsignedByte()
                anInt3008 = packet.readShort()
            }
            100 -> {
                anInt3038 = packet.readUnsignedByte()
                anInt3013 = packet.readShort()
            }
            101 -> anInt2958 = packet.readUnsignedByte()
            102 -> anInt3006 = packet.readShort()
            103 -> culling = 0
            104 -> anInt3024 = packet.readUnsignedByte()
            105 -> aBoolean3007 = true
            106 -> {
                val length = packet.readUnsignedByte()
                var total = 0
                animations = IntArray(length)
                anIntArray2995 = IntArray(length)
                repeat(length) { count ->
                    animations!![count] = packet.readShort()
                    if (animations!![count] == 65535) {
                        animations!![count] = -1
                    }
                    anIntArray2995!![count] = packet.readUnsignedByte()
                    total += anIntArray2995!![count]
                }
                repeat(length) { count ->
                    anIntArray2995!![count] = 65535 * anIntArray2995!![count] / total
                }
            }
            107 -> mapDefinitionId = packet.readShort()
            in 150..154 -> {
                options[-150 + opcode] = packet.readString()
                if (!member) {
                    options[-150 + opcode] = null
                }
            }
            160 -> {
                val length = packet.readUnsignedByte()
                anIntArray2981 = IntArray(length)
                repeat(length) { count ->
                    anIntArray2981!![count] = packet.readShort()
                }
            }
            162 -> {
                contouredGround = 3.toByte()
                anInt3023 = packet.readInt()
            }
            163 -> {
                aByte2974 = packet.readByte().toByte()
                aByte3045 = packet.readByte().toByte()
                aByte3052 = packet.readByte().toByte()
                aByte2960 = packet.readByte().toByte()
            }
            164 -> anInt2964 = packet.readUnsignedShort()
            165 -> anInt2963 = packet.readUnsignedShort()
            166 -> anInt3018 = packet.readUnsignedShort()
            167 -> anInt2983 = packet.readShort()
            168 -> aBoolean2961 = true
            169 -> aBoolean2993 = true
            170 -> anInt3032 = packet.readSmart()
            171 -> anInt2962 = packet.readSmart()
            173 -> {
                anInt3050 = packet.readShort()
                anInt3020 = packet.readShort()
            }
            177 -> aBoolean2992 = true
            178 -> anInt2975 = packet.readUnsignedByte()
            249 -> {
                val length = packet.readUnsignedByte()
                if (params == null) {
                    val initialCapacity: Int = calculateCapacity(length)
                    params = HashMap(initialCapacity)
                }
                repeat(length) {
                    val isString = packet.readUnsignedByte() == 1
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

    fun changeValues() {
        if (interactive == -1) {
            interactive = 0
            if (modelIds != null && modelIds!!.size == 1 && modelIds!![0] == 10.toByte()) {
                interactive = 1
            }
            for (index in 0..4) {
                if (options[index] != null) {
                    interactive = 1
                    break
                }
            }
        }
        if (supportItems == -1) {
            supportItems = if (solid == 0) 0 else 1
        }
        if (animations != null || aBoolean2998 || configObjectIds != null) {
            aBoolean2992 = true
        }
    }

    companion object {
        const val lowDetail = true
    }
}