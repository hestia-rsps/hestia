package worlds.gregs.hestia.network.game.out

import world.gregs.hestia.core.network.packets.Packet

class ComponentSettings(interfaceId: Int, componentId: Int, fromSlot: Int, toSlot: Int, settings: Int) : Packet.Builder(3) {
    init {
        writeLEShort(fromSlot)
        writeInt2(interfaceId shl 16 or componentId)
        writeShortA(toSlot)
        writeLEInt(settings)
    }
}