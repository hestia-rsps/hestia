package worlds.gregs.hestia.network.social.out

import world.gregs.hestia.core.network.packets.Packet
import worlds.gregs.hestia.GameServer

class WorldInfo : Packet.Builder(11, Packet.Type.VAR_BYTE) {
    init {
        val info = GameServer.worldDetails
        writeByte(info.id)
        writeByte(info.location)
        writeShort(info.flag)
        writeString(info.activity)
        writeString(info.ip)
        writeString(info.region)
        writeByte(info.country)
    }
}