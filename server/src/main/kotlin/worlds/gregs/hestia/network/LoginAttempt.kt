package worlds.gregs.hestia.network

import com.artemis.World
import net.mostlyoriginal.api.event.common.EventSystem
import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.codec.inbound.InboundHandler
import world.gregs.hestia.core.network.login.LoginHandshake
import world.gregs.hestia.core.network.login.LoginRequestListener
import world.gregs.hestia.core.network.packets.InboundPacket
import world.gregs.hestia.core.network.packets.Packet
import worlds.gregs.hestia.network.game.`in`.GamePacket
import worlds.gregs.hestia.network.login.`in`.WorldLogin
import worlds.gregs.hestia.services.getSystem

class LoginAttempt(private val w: World, private val worldHandler: InboundHandler) : LoginRequestListener {
    override fun login(session: Session, handler: InboundPacket, packet: Packet, password: String, serverSeed: Long, clientSeed: Long) {
        if(handler is GamePacket) {
            handler.entity = if(session.id != -1) w.getEntity(session.id) else null
            handler.es = w.getSystem(EventSystem::class)
            handler.read(session, packet, packet.readableBytes())
        } else {
            handler.read(session, packet, packet.readableBytes())
        }
        if(handler is WorldLogin) {
            session.channel?.pipeline()?.replace(LoginHandshake::class.java, "handler", worldHandler)
        }
    }
}