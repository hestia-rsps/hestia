package worlds.gregs.hestia.network.world.out

import world.gregs.hestia.core.WorldDetails
import world.gregs.hestia.core.network.packets.Packet

class WorldInfo(info: WorldDetails) : Packet.Builder(11, Packet.Type.VAR_BYTE) {
    init {
        writeByte(info.id)
        writeByte(info.location)
        writeShort(info.flag)
        writeString(info.activity)
        writeString(info.ip)
        writeString(info.region)
        writeByte(info.country)
    }
}