package worlds.gregs.hestia.network.game.out

import world.gregs.hestia.core.network.packets.Packet

class PlayerOnInterface(interfaceId: Int, componentId: Int) : Packet.Builder(114) {

    init {
        writeLEInt(interfaceId shl 16 or componentId)
    }

}