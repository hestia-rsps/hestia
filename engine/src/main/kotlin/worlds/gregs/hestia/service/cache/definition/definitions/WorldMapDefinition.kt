package worlds.gregs.hestia.service.cache.definition.definitions

import world.gregs.hestia.core.network.codec.packet.Packet
import worlds.gregs.hestia.service.cache.Definition
import java.util.*

class WorldMapDefinition : Definition {
    var maxX = 0
    var position: Int = -1
    var map: String = ""
    var minY = 12800
    var minX = 12800
    var anInt9542: Int = -1
    var name: String = ""
    var static: Boolean = false
    var maxY = 0
    var id: Int = -1
    var anInt9547: Int = -1
    var sections: LinkedList<WorldMapSectionDefinition>? = null

    override fun readValueLoop(packet: Packet, member: Boolean) {
        map = packet.readString()
        name = packet.readString()
        position = packet.readInt()
        anInt9542 = packet.readInt()//Size?
        static = packet.readUnsignedBoolean()
        anInt9547 = packet.readUnsignedByte()//Always zero except "Braindeath Island" which is -1
        packet.readUnsignedByte()

        if (anInt9547 == 255) {
            anInt9547 = 0
        }
        sections = LinkedList()
        val length = packet.readUnsignedByte()
        repeat(length) {
            sections!!.addLast(WorldMapSectionDefinition(packet.readUnsignedByte(), packet.readShort(), packet.readShort(), packet.readShort(), packet.readShort(), packet.readShort(), packet.readShort(), packet.readShort(), packet.readShort()))
        }
    }

    override fun readValues(opcode: Int, packet: Packet, member: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun changeValues() {
        maxY = 0
        minX = 12800
        minY = 12800
        maxX = 0

        sections!!.forEach { worldMapAreaDefinition ->
            if (minY > worldMapAreaDefinition.startY) {
                minY = worldMapAreaDefinition.startY
            }
            if (worldMapAreaDefinition.endX > maxX) {
                maxX = worldMapAreaDefinition.endX
            }
            if (maxY < worldMapAreaDefinition.endY) {
                maxY = worldMapAreaDefinition.endY
            }
            if (worldMapAreaDefinition.startX < minX) {
                minX = worldMapAreaDefinition.startX
            }
        }
    }
}