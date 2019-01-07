package worlds.gregs.hestia.network.out

import world.gregs.hestia.core.network.packets.Packet

class WindowsPane(id: Int, type: Int) : Packet.Builder(67) {

    init {
        writeLEShortA(id)
        writeByteS(type)
    }

}