package worlds.gregs.hestia.network.login.out

import world.gregs.hestia.core.network.packets.Packet

class LoginDetails(index: Int, name: String, rights: Int) : Packet.Builder(2, Packet.Type.VAR_BYTE) {
    init {
        writeByte(rights)//Rights
        writeByte(0)
        writeByte(0)
        writeByte(0)
        writeByte(1)
        writeByte(0)
        writeShort(index)//Player index
        writeByte(1)
        writeMedium(0)
        writeByte(1)
        writeString(name)//Display name
    }
}