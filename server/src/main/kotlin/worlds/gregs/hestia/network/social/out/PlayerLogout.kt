package worlds.gregs.hestia.network.social.out

import world.gregs.hestia.core.network.packets.Packet

class PlayerLogout(entityId: Int) : Packet.Builder(4) {
    init {
        writeInt(entityId)
    }
}