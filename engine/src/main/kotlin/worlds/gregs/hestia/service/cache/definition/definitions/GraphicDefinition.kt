package worlds.gregs.hestia.service.cache.definition.definitions

import world.gregs.hestia.core.network.codec.packet.Packet
import worlds.gregs.hestia.service.cache.Definition

class GraphicDefinition : Definition {
    var id = 0
    var modelId = 0
    var aByte2381: Byte = 0
    var ambience = 0
    var modifiedColours: ShortArray? = null
    var anInt2385: Int = -1
    var originalColours: ShortArray? = null
    var rotation: Int = 0
    var originalTextureColours: ShortArray? = null
    var contrast: Int = 0
    var animationId: Int = -1
    var modifiedTextureColours: ShortArray? = null
    var sizeZ: Int = 128
    var sizeXY: Int = 128
    var aBoolean2402: Boolean = false

    override fun readValues(opcode: Int, packet: Packet, member: Boolean) {
        when (opcode) {
            1 -> modelId = packet.readShort()
            2 -> animationId = packet.readShort()
            4 -> sizeXY = packet.readShort()
            5 -> sizeZ = packet.readShort()
            6 -> rotation = packet.readShort()
            7 -> ambience = packet.readUnsignedByte()
            8 -> contrast = packet.readUnsignedByte()
            9 -> {
                aByte2381 = 3.toByte()
                anInt2385 = 8224
            }
            10 -> aBoolean2402 = true
            11 -> aByte2381 = 1.toByte()
            12 -> aByte2381 = 4.toByte()
            13 -> aByte2381 = 5.toByte()
            14 -> {
                aByte2381 = 2.toByte()
                anInt2385 = 256 * packet.readUnsignedByte()
            }
            15 -> {
                aByte2381 = 3.toByte()
                anInt2385 = packet.readShort()
            }
            16 -> {
                aByte2381 = 3.toByte()
                anInt2385 = packet.readInt()
            }
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
                modifiedTextureColours = ShortArray(length)
                originalTextureColours = ShortArray(length)
                repeat(length) { count ->
                    originalTextureColours!![count] = packet.readShort().toShort()
                    modifiedTextureColours!![count] = packet.readShort().toShort()
                }
            }
        }
    }
}