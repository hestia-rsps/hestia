package worlds.gregs.hestia.network.social.out

import world.gregs.hestia.core.network.packets.Packet

class PlayerLogin(id: Int, packet: Packet) : Packet.Builder(8, Packet.Type.VAR_SHORT) {
    init {
        writeShort(id)
        writeBytes(packet.buffer)
    }
}