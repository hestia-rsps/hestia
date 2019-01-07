package worlds.gregs.hestia.network.out

import world.gregs.hestia.core.network.packets.Packet

class ItemOnInterface(interfaceId: Int, componentId: Int, id: Int, amount: Int) : Packet.Builder(9) {

    init {
        writeLEShort(id)
        writeInt(amount)
        writeInt2(interfaceId shl 16 or componentId)
    }

}