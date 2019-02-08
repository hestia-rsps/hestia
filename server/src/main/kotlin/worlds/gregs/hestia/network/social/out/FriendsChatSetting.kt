package worlds.gregs.hestia.network.social.out

import world.gregs.hestia.core.network.packets.Packet

class FriendsChatSetting(entityId: Int, setting: Int, option: Int) : Packet.Builder(6, Packet.Type.VAR_BYTE) {
    init {
        writeInt(entityId)
        writeInt(setting)
        writeByte(option)
    }
}