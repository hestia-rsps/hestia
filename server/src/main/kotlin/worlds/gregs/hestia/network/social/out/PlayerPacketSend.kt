package worlds.gregs.hestia.network.social.out

import world.gregs.hestia.core.network.packets.Packet

class PlayerPacketSend(entityId: Int, packet: Packet) : Packet.Builder(1, Packet.Type.VAR_SHORT) {
    init {
        writeInt(entityId)
        writeByte(packet.opcode)
        writeBytes(packet.buffer)
    }
}