package worlds.gregs.hestia.network.game.`in`

import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.packets.InboundPacket
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketInfo
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.network.social.out.PlayerLogin

@PacketInfo(-2, 16)
class GameLogin : InboundPacket {

    override fun read(session: Session, packet: Packet) {
        val index = (1..Short.MAX_VALUE)
                .firstOrNull { it > 0 && !GameServer.loginSessions.containsKey(it) } ?: throw IllegalStateException("Maximum player login session reached")
        GameServer.loginSessions[index] = session
        GameServer.worldSession?.write(PlayerLogin(index, packet))
    }
}