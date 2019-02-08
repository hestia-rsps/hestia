package worlds.gregs.hestia.network.social.`in`

import org.slf4j.LoggerFactory
import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.packets.InboundPacket
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketInfo
import world.gregs.hestia.core.network.packets.out.Response
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.game.events.CreatePlayer

@PacketInfo(-1, 4)
class PlayerLoginResponse : InboundPacket {
    private val logger = LoggerFactory.getLogger(PlayerLoginResponse::class.java)!!
    override fun read(session: Session, packet: Packet) {
        val sessionIndex = packet.readShort()
        val playerSession = GameServer.loginSessions[sessionIndex]
        val response = Response.values()[packet.readByte()]
        if (playerSession == null) {
            logger.info("Error finding session $sessionIndex $response")
            return
        }
        if(response == Response.NORMAL) {
            val name = packet.readString()
            val mode = packet.readByte()
            val width = packet.readShort()
            val height = packet.readShort()

            GameServer.eventSystem.dispatch(CreatePlayer(playerSession, name, mode, width, height))
        } else {
            playerSession.respond(response)
        }
        GameServer.loginSessions.remove(sessionIndex)
    }

}