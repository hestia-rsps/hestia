package worlds.gregs.hestia.network.social.out

import world.gregs.hestia.core.network.packets.Packet

class FriendsChatSetupRequest(entityId: Int) : Packet.Builder(7) {
    init {
        writeInt(entityId)
    }
}