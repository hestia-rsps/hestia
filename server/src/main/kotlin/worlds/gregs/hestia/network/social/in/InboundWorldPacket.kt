package worlds.gregs.hestia.network.social.`in`

import io.netty.buffer.Unpooled
import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.packets.InboundPacket
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketInfo
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.game.events.WorldPacket

@PacketInfo(-2, 3)
class InboundWorldPacket : InboundPacket {
    override fun read(session: Session, packet: Packet) {
        val entityId = packet.readInt()
        val buffer = Unpooled.copiedBuffer(packet.buffer).discardReadBytes()
        val opcode = packet.readUnsignedByte()
        GameServer.eventSystem.dispatch(WorldPacket(entityId, Packet(opcode = opcode, buffer = buffer)))
    }

}