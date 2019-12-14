package worlds.gregs.hestia.service.cache.config.definitions

import world.gregs.hestia.core.network.codec.packet.Packet
import worlds.gregs.hestia.service.cache.Definition

class HitSplatDefinition : Definition {
    var anInt3214: Int = 0
    var fade = -1
    var left: Int = -1
    var middle: Int = -1
    var icon: Int = -1
    var amount = ""
    var offsetX: Int = 0
    var right: Int = -1
    var textColour: Int = 16777215
    var offsetY: Int = 0
    var duration: Int = 70
    var comparisonType: Int = -1
    var font: Int = -1

    override fun readValues(opcode: Int, packet: Packet, member: Boolean) {
        when (opcode) {
            1 -> font = packet.readShort()
            2 -> textColour = packet.readMedium()
            3 -> icon = packet.readShort()
            4 -> left = packet.readShort()
            5 -> middle = packet.readShort()
            6 -> right = packet.readShort()
            7 -> offsetX = packet.readUnsignedShort()
            8 -> amount = packet.readString()
            9 -> duration = packet.readShort()
            10 -> offsetY = packet.readUnsignedShort()
            11 -> fade = 0
            12 -> comparisonType = packet.readUnsignedByte()
            13 -> anInt3214 = packet.readUnsignedShort()
            14 -> fade = packet.readShort()
        }
    }
}