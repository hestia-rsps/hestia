package worlds.gregs.hestia.network.social.`in`

import io.netty.buffer.Unpooled
import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.packets.InboundPacket
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketInfo
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.game.events.OutBoundPacket

@PacketInfo(-2, 2)
class PlayerPacket : InboundPacket {
    override fun read(session: Session, packet: Packet) {
        val entityId = packet.readInt()
        val buffer = Unpooled.copiedBuffer(packet.buffer).discardReadBytes()
        //No need for opcode and type as packet has already been encoded
        GameServer.eventSystem.dispatch(OutBoundPacket(entityId, Packet(buffer = buffer)))
    }

}