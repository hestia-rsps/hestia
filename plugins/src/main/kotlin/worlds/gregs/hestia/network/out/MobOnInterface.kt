package worlds.gregs.hestia.network.out

import world.gregs.hestia.core.network.packets.Packet

class MobOnInterface(interfaceId: Int, componentId: Int, id: Int) : Packet.Builder(98) {

    init {
        writeInt(interfaceId shl 16 or componentId)
        writeLEShort(id)
    }

}