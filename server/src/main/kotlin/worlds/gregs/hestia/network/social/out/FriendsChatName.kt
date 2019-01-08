package worlds.gregs.hestia.network.social.out

import world.gregs.hestia.core.network.packets.Packet

class FriendsChatName(entityId: Int, name: String) : Packet.Builder(5, Packet.Type.VAR_BYTE) {
    init {
        writeInt(entityId)
        writeString(name)
    }
}