package worlds.gregs.hestia.network

import com.artemis.World
import net.mostlyoriginal.api.event.common.EventSystem
import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.codec.decode.PacketDecoder
import world.gregs.hestia.core.network.codec.inbound.ConnectionListener
import world.gregs.hestia.core.network.codec.inbound.packet.PacketInboundHandler
import world.gregs.hestia.core.network.login.LoginHandshake
import world.gregs.hestia.core.network.login.LoginRequestListener
import world.gregs.hestia.core.network.packets.InboundPacket
import world.gregs.hestia.core.network.packets.Packet
import worlds.gregs.hestia.game.PacketHandlerSystem
import worlds.gregs.hestia.network.game.`in`.GameLogin
import worlds.gregs.hestia.network.game.`in`.GamePacket
import worlds.gregs.hestia.services.getSystem
import kotlin.reflect.KClass

class LoginAttempt(private val w: World, private val decoder: KClass<out PacketDecoder>, private val worldHandler: PacketInboundHandler<PacketHandlerSystem>, private val sessionHandler: ConnectionListener) : LoginRequestListener {

    override fun login(session: Session, handler: InboundPacket, packet: Packet, password: String, serverSeed: Long, clientSeed: Long) {
        if(handler is GamePacket) {
            handler.entity = if(session.id != -1) w.getEntity(session.id) else null
            handler.es = w.getSystem(EventSystem::class)
            handler.read(session, packet)
        } else {
            handler.read(session, packet)
        }
        if(handler is GameLogin) {
            session.channel?.pipeline()?.replace(LoginHandshake::class.java, "decoder", decoder.java.getDeclaredConstructor().newInstance())
            session.channel?.pipeline()?.addAfter("decoder", "handler", worldHandler)
            session.channel?.pipeline()?.addAfter("handler", "session", sessionHandler)
        }
    }
}