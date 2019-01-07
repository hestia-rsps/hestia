package worlds.gregs.hestia.network.out

import world.gregs.hestia.core.network.packets.Packet

class UnlockComponentOption(interfaceId: Int, componentId: Int, fromSlot: Int, toSlot: Int, vararg optionsSlots: Int) : Packet.Builder(3) {
    init {
        var settings = 0
        for(slot in optionsSlots) {
            settings = settings or (2 shl slot)
        }
        //TODO duplicate of ComponentSettings
        writeLEShort(fromSlot)
        writeInt2(interfaceId shl 16 or componentId)
        writeShortA(toSlot)
        writeLEInt(settings)
    }
}