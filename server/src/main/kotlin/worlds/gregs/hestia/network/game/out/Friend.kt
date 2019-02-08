package worlds.gregs.hestia.network.game.out

import world.gregs.hestia.core.Settings
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.services.int

class Friend(username: String, displayName: String, world: Int, online: Boolean, warn: Boolean) : Packet.Builder(85, Packet.Type.VAR_SHORT) {
    init {
        writeByte((!warn).int)
        writeString(displayName)
        writeString(if (displayName == username) "" else username)
        writeShort(if (online) world else 0)
        writeByte(0)//player.getFriendsIgnores().getRank(TextUtils.formatPlayerNameForProtocol(username)))
        writeByte(0)
        if (online) {
            writeString(Settings.getString("serverName", ""))
            writeByte(0)
        }
    }
}