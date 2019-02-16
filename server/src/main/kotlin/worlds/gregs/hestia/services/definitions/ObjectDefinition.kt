package worlds.gregs.hestia.services.definitions

import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.services.int

class ObjectDefinition {

    private var originalColours: ShortArray? = null
    lateinit var configObjectIds: IntArray
    private var anIntArray3833: IntArray? = null
    private var anInt3834 = 0
    private var anInt3835 = -1
    private var aByte3837: Byte = 0
    private var anInt3838 = -1
    private var rotated = false
    private var contrast = 0
    private var modelSizeH = 128
    private var anInt3844 = -1
    private var aBoolean3845 = false
    private var aByte3847: Byte = 0
    private var aByte3849: Byte = 0
    private var anInt3850 = 0
    private var mapDefinitionId = -1
    var obstructsGround = false
    var aBoolean3853 = true
    private var supportItems = -1
    var ignoreClipOnAlternativeRoute = false
    private var anInt3857 = -1
    private var aByteArray3858: ByteArray? = null
    lateinit var anIntArray3859: IntArray
    private var anInt3860 = -1
    private var options: Array<String?> = arrayOfNulls(5)
    var varbitIndex: Int = -1
        private set
    private var modifiedColours: ShortArray? = null
    private var anInt3865 = 255
    private var aBoolean3866 = false
    private var delayShading = false
    var isProjectileClipped = true
    private var animationArray: IntArray? = null
    private var aBoolean3870 = false
    var sizeY: Short = 1
    private var castsShadow = true
    private var aBoolean3873 = false
    var cullingType = -1
    private var anInt3875 = 0
    var animationId = -1
    private var anInt3877 = 0
    private var brightness: Byte = 0
    var solid = 2
    private var animationIdTotal = 0
    private var anInt3882 = -1
    private var offsetX = 0
    private var offsetH = 0
    var sizeX: Short = 1
    var aBoolean3891 = false
    private var offsetMultiplier = 64
    var interactive = -1
    private var aBoolean3894 = false
    private var animateImmediately = true
    private var configId = -1
    private var modelTypes: ByteArray? = null
    private var anInt3900 = 0
    var name = "null"
    private var modelSizeX = 128
    private var anInt3904: Short = 0
    private var anInt3905 = 0
    private var aBoolean3906 = false
    lateinit var anIntArray3908: IntArray
    private var contouredGround: Byte = 0
    private var anInt3913 = -1
    private var aByte3914: Byte = 0
    private var offsetY = 0
    private lateinit var modelIds: Array<IntArray?>
    private var modelSizeY = 128
    private var originalModelTextures: ShortArray? = null
    private var modifiedModelTextures: ShortArray? = null
    private var anInt3921 = 0
    private var parameters: HashMap<Int, Any>? = null
    private var aBoolean3923 = false
    private var aBoolean3924 = false
    var accessBlockFlag = 0
        private set
    var id: Int = 0

    fun getOption(option: Int): String? {
        val index = option - 1
        if (index !in options.indices) {
            return null
        }
        return options[index]
    }

    fun hasOption(option: String): Boolean {
        return options.any { it?.equals(option, true) ?: false }
    }

    private fun readValues(buffer: Packet, opcode: Int) {
        when (opcode) {
            1, 5 -> {
                val lowDetail = false
                if (opcode == 5 && lowDetail) {
                    skip(buffer)
                }
                val length = buffer.readUnsignedByte()
                modelIds = arrayOfNulls(length)
                modelTypes = ByteArray(length)
                for (index in 0 until length) {
                    modelTypes!![index] = buffer.readByte().toByte()
                    val size = buffer.readUnsignedByte()
                    modelIds[index] = IntArray(size)
                    for (subIndex in 0 until size) {
                        modelIds[index]!![subIndex] = buffer.readShort()
                    }
                }
                if (opcode == 5 && !lowDetail) {
                    skip(buffer)
                }
            }
            2 -> name = buffer.readString()
            14 -> sizeX = buffer.readUnsignedByte().toShort()
            15 -> sizeY = buffer.readUnsignedByte().toShort()
            17 -> {
                isProjectileClipped = false
                solid = 0
            }
            18 -> isProjectileClipped = false
            19 -> interactive = buffer.readUnsignedByte()
            21 -> contouredGround = 1.toByte()
            22 -> delayShading = true
            23 -> cullingType = 1
            24 -> animationId = buffer.readShort()
            27 -> solid = 1
            28 -> offsetMultiplier = buffer.readUnsignedByte() shl 2
            29 -> brightness = buffer.readByte().toByte()
            in 30..34 -> options[opcode - 30] = buffer.readString()
            39 -> contrast = buffer.readByte() * 5
            40 -> {
                val length = buffer.readUnsignedByte()
                originalColours = ShortArray(length)
                modifiedColours = ShortArray(length)
                for (index in 0 until length) {
                    originalColours!![index] = buffer.readUnsignedShort().toShort()
                    modifiedColours!![index] = buffer.readUnsignedShort().toShort()
                }
            }
            41 -> {
                val length = buffer.readUnsignedByte()
                modifiedModelTextures = ShortArray(length)
                originalModelTextures = ShortArray(length)
                for (index in 0 until length) {
                    modifiedModelTextures!![index] = buffer.readUnsignedShort().toShort()
                    originalModelTextures!![index] = buffer.readUnsignedShort().toShort()
                }
            }
            42 -> {
                val length = buffer.readUnsignedByte()
                aByteArray3858 = ByteArray(length)
                for (index in 0 until length) {
                    aByteArray3858!![index] = buffer.readByte().toByte()
                }
            }
            62 -> rotated = true
            64 -> castsShadow = false
            65 -> modelSizeX = buffer.readUnsignedShort()
            66 -> modelSizeH = buffer.readUnsignedShort()
            67 -> modelSizeY = buffer.readUnsignedShort()
            69 -> accessBlockFlag = buffer.readUnsignedByte()
            70 -> offsetX = buffer.readShort() shl 2
            71 -> offsetH = buffer.readShort() shl 2
            72 -> offsetY = buffer.readShort() shl 2
            73 -> obstructsGround = true
            74 -> ignoreClipOnAlternativeRoute = true
            75 -> supportItems = buffer.readUnsignedByte()
            77, 92 -> {
                varbitIndex = buffer.readUnsignedShort()
                if (varbitIndex == 65535) {
                    varbitIndex = -1
                }
                configId = buffer.readUnsignedShort()
                if (configId == 65535) {
                    configId = -1
                }
                var tail = -1
                if (opcode == 92) {
                    tail = buffer.readShort()
                }
                val size = buffer.readUnsignedByte()
                configObjectIds = IntArray(size + 2)
                for (index in 0..size) {
                    configObjectIds[index] = buffer.readShort()
                }
                configObjectIds[size + 1] = tail
            }
            78 -> {
                anInt3860 = buffer.readUnsignedShort()
                anInt3904 = buffer.readUnsignedByte().toShort()
            }
            79 -> {
                anInt3900 = buffer.readUnsignedShort()
                anInt3905 = buffer.readUnsignedShort()
                anInt3904 = buffer.readUnsignedByte().toShort()
                val length = buffer.readUnsignedByte()
                anIntArray3859 = IntArray(length)
                for (index in 0 until length)
                    anIntArray3859[index] = buffer.readUnsignedShort()
            }
            81 -> {
                contouredGround = 2.toByte()
                anInt3882 = 256 * buffer.readUnsignedByte()
            }
            82 -> aBoolean3891 = true
            88 -> aBoolean3853 = false
            89 -> animateImmediately = false
            90 -> aBoolean3870 = true
            91 -> aBoolean3873 = true
            93 -> {
                contouredGround = 3.toByte()
                anInt3882 = buffer.readUnsignedShort()
            }
            94 -> contouredGround = 4.toByte()
            95 -> {
                contouredGround = 5.toByte()
                anInt3882 = buffer.readShort()
            }
            96 -> aBoolean3924 = true
            97 -> aBoolean3866 = true
            98 -> aBoolean3923 = true
            99 -> {
                anInt3857 = buffer.readUnsignedByte()
                anInt3835 = buffer.readUnsignedShort()
            }
            100 -> {
                anInt3844 = buffer.readUnsignedByte()
                anInt3913 = buffer.readUnsignedShort()
            }
            101 -> anInt3850 = buffer.readUnsignedByte()
            102 -> anInt3838 = buffer.readUnsignedShort()
            103 -> cullingType = 0
            104 -> anInt3865 = buffer.readUnsignedByte()
            105 -> aBoolean3906 = true
            106 -> {
                val length = buffer.readUnsignedByte()
                animationArray = IntArray(length)
                anIntArray3833 = IntArray(length)
                for (index in 0 until length) {
                    anIntArray3833!![index] = buffer.readShort()
                    val value = buffer.readUnsignedByte()
                    animationArray!![index] = value
                    animationIdTotal += value
                }
            }
            107 -> mapDefinitionId = buffer.readUnsignedShort()
            in 150..154 -> options[opcode - 150] = buffer.readString()
            160 -> {
                val length = buffer.readUnsignedByte()
                anIntArray3908 = IntArray(length)
                for (index in 0 until length) {
                    anIntArray3908[index] = buffer.readUnsignedShort()
                }
            }
            162 -> {
                contouredGround = 3.toByte()
                anInt3882 = buffer.readInt()
            }
            163 -> {
                aByte3847 = buffer.readByte().toByte()
                aByte3849 = buffer.readByte().toByte()
                aByte3837 = buffer.readByte().toByte()
                aByte3914 = buffer.readByte().toByte()
            }
            164 -> anInt3834 = buffer.readShort()
            165 -> anInt3875 = buffer.readShort()
            166 -> anInt3877 = buffer.readShort()
            167 -> anInt3921 = buffer.readUnsignedShort()
            168 -> aBoolean3894 = true
            169 -> aBoolean3845 = true
            170 -> {
                val anInt3383 = buffer.readSmart()
            }
            171 -> {
                val anInt3362 = buffer.readSmart()
            }
            173 -> {
                val anInt3302 = buffer.readUnsignedShort()
                val anInt3336 = buffer.readUnsignedShort()
            }
            177 -> {
                val ub = true
            }
            178 -> {
                val db = buffer.readUnsignedByte()
            }
            189 -> {
                val bloom = true
            }
            249 -> {
                val length = buffer.readUnsignedByte()
                if (parameters == null) {
                    parameters = HashMap(length)
                }
                for (index in 0 until length) {
                    val isString = buffer.readUnsignedByte() == 1
                    val id = buffer.readMedium()
                    parameters!![id] = if (isString) {
                        buffer.readString()
                    } else {
                        buffer.readInt()
                    }
                }
            }
        }
    }

    private fun skip(buffer: Packet) {
        val length = buffer.readUnsignedByte()
        for (index in 0 until length) {
            buffer.skip(1)
            val amount = buffer.readUnsignedByte()
            buffer.skip(amount * 2)
        }
    }

    fun readValueLoop(stream: Packet) {
        while (true) {
            val opcode = stream.readUnsignedByte()
            if (opcode == 0) {
                break
            }
            readValues(stream, opcode)
        }
    }

    fun changeValues() {
        if (interactive == -1) {
            interactive = if (modelTypes != null && modelTypes!!.size == 1 && modelTypes!![0].toInt() == 10) {
                1
            } else {
                options.filterNotNull().any().int
            }
        }
        if (supportItems == -1) {
            supportItems = if (solid != 0) 1 else 0
        }
    }
}
