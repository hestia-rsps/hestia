package worlds.gregs.hestia.network.game

import io.netty.channel.ChannelHandler
import net.mostlyoriginal.api.event.common.EventSystem
import org.slf4j.LoggerFactory
import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.codec.inbound.packet.PacketInboundHandler
import world.gregs.hestia.core.network.packets.Packet
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.GameServer.Companion.socialPackets
import worlds.gregs.hestia.game.PacketHandlerSystem
import worlds.gregs.hestia.game.events.ClientPacket
import worlds.gregs.hestia.network.social.out.PlayerPacketSend
import worlds.gregs.hestia.services.getSystem

@ChannelHandler.Sharable
class GamePacketHandler : PacketInboundHandler<PacketHandlerSystem>() {

    override val logger = LoggerFactory.getLogger(GamePacketHandler::class.java)!!

    override fun getHandler(opcode: Int): PacketHandlerSystem? {
        return PacketHandlerSystem.gamePackets.getPacket(opcode)
    }

    override fun process(session: Session, packet: Packet) {
        //Redirect packets to the social server
        if (socialPackets.containsKey(packet.opcode)) {
            GameServer.worldSession?.write(PlayerPacketSend(session.id, packet))
        } else {
            super.process(session, packet)
        }
    }

    override fun process(session: Session, handler: PacketHandlerSystem?, packet: Packet) {
        GameServer.server.server?.getSystem(EventSystem::class)?.dispatch(ClientPacket(session.id, packet))
    }
}