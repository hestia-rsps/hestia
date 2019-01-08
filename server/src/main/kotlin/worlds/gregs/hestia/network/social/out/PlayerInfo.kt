package worlds.gregs.hestia.network.social.out

import world.gregs.hestia.core.network.packets.Packet

class PlayerInfo(name: String, entityId: Int, index: Int, reconnect: Boolean = false) : Packet.Builder(if(reconnect) 3 else 2, Packet.Type.VAR_BYTE) {
    init {
        writeString(name)
        writeInt(entityId)
        writeShort(index)
    }
}