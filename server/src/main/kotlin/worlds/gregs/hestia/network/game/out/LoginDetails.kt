package worlds.gregs.hestia.network.game.out

import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.services.int

class LoginDetails(index: Int, name: String, rights: Int, isMember: Boolean, membersWorld: Boolean) : Packet.Builder(2, Packet.Type.VAR_BYTE) {
    init {
        writeByte(rights)//Rights
        writeByte(0)//Unknown - something to do with skipping chat messages
        writeByte(0)
        writeByte(0)
        writeByte(0)
        writeByte(0)//Moves chat box position
        writeShort(index)//Player index
        writeByte(isMember.int)
        writeMedium(0)
        writeByte(membersWorld.int)
        writeString(name)//Display name
    }
}