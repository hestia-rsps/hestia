package worlds.gregs.hestia.network.social.`in`

import org.slf4j.LoggerFactory
import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.packets.InboundPacket
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketInfo
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.GameServer.Companion.eventSystem
import worlds.gregs.hestia.GameServer.Companion.socialPackets
import worlds.gregs.hestia.game.events.SocialLogin
import worlds.gregs.hestia.services.players

@PacketInfo(-1, 11)
class WorldRegistered : InboundPacket {

    private val logger = LoggerFactory.getLogger(WorldRegistered::class.java)

    override fun read(session: Session, packet: Packet) {
        val id = packet.readByte()
        val details = GameServer.worldDetails

        if (details.id != -1 && id != details.id) {
            logger.warn("World id mismatch $id ${details.id}")
            session.close()
            return
        }

        GameServer.worldSession = session
        details.id = id
        logger.info("Registered as World $id")
        GameServer.server.init(id)

        socialPackets.clear()
        val count = packet.readByte()
        for (i in 0 until count) {
            val opcode = packet.readByte()
            val size = packet.readByte()
            if (opcode != 16) {//TODO better way of handling overlapping packets
                socialPackets[opcode] = size
            }
        }

        //Notify social server of all the current players
        GameServer.server.server?.players()?.forEach {
            eventSystem.dispatch(SocialLogin(it))
        }
    }

}