package worlds.gregs.hestia.service.cache.config.definitions

import world.gregs.hestia.core.network.codec.packet.Packet
import worlds.gregs.hestia.service.cache.Definition

class IdentityKitDefinition : Definition {
    var originalColours: ShortArray? = null
    var modifiedTextureColours: ShortArray? = null
    var originalTextureColours: ShortArray? = null
    var modelIds: IntArray? = null
    var modifiedColours: ShortArray? = null
    val headModels = intArrayOf(-1, -1, -1, -1, -1)

    override fun readValues(opcode: Int, packet: Packet, member: Boolean) {
        when(opcode) {
            1 -> packet.readUnsignedByte()
            2 -> {
                val length = packet.readUnsignedByte()
                modelIds = IntArray(length)
                repeat(length) { count ->
                    modelIds!![count] = packet.readUnsignedShort()
                }
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
            in 60..69 -> headModels[opcode - 60] = packet.readUnsignedShort()
        }
    }
}