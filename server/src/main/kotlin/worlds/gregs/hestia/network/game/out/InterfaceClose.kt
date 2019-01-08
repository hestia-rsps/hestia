package worlds.gregs.hestia.network.game.out

import world.gregs.hestia.core.network.packets.Packet

class InterfaceClose(windowId: Int, windowComponentId: Int) : Packet.Builder(73) {

    init {
        writeLEInt(windowId shl 16 or windowComponentId)
    }

}