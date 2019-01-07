package worlds.gregs.hestia.network.out

import world.gregs.hestia.core.network.packets.Packet

class InterfaceComponentAnimation(interfaceId: Int, componentId: Int, id: Int) : Packet.Builder(23) {

    init {
        writeLEShortA(id)
        writeInt1(interfaceId shl 16 or componentId)
    }

}