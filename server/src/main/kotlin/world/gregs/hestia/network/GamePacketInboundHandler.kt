package world.gregs.hestia.network

import com.artemis.World
import net.mostlyoriginal.api.event.common.EventSystem
import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.codec.inbound.PacketInboundHandler
import world.gregs.hestia.core.network.packets.InboundPacket
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.services.load.PacketMap
import world.gregs.hestia.network.game.GamePacket
import world.gregs.hestia.services.getSystem

class GamePacketInboundHandler(private val world: World, packets: PacketMap) : PacketInboundHandler(packets) {

    override fun process(session: Session, handler: InboundPacket, packet: Packet, length: Int) {
        if(handler is GamePacket) {
            handler.entity = if(session.id != -1) world.getEntity(session.id) else null
            handler.es = world.getSystem(EventSystem::class)
            handler.read(session, packet, length)
        } else {
            handler.read(session, packet, length)
        }
    }

    override fun disconnect(session: Session) {
        if(session.id != -1) {
            world.delete(session.id)
        }
    }
}