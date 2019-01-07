package worlds.gregs.hestia.network.out

import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.services.int

class InterfaceOpen(permanent: Boolean, windowId: Int, windowComponentId: Int, interfaceId: Int) : Packet.Builder(5) {

    init {
        writeLEShortA(interfaceId)
        writeLEInt(windowId shl 16 or windowComponentId)
        writeByte(permanent.int)
    }

}