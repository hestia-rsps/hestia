package worlds.gregs.hestia.network

import com.artemis.World
import net.mostlyoriginal.api.event.common.EventSystem
import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.codec.inbound.PacketInboundHandler
import world.gregs.hestia.core.network.packets.Packet
import worlds.gregs.hestia.game.PacketHandler
import worlds.gregs.hestia.game.events.ClientPacket
import worlds.gregs.hestia.services.getSystem

class GamePacketInboundHandler(private val world: World) : PacketInboundHandler<PacketHandler>(PacketHandler.gamePackets) {

    override fun process(session: Session, handler: PacketHandler, packet: Packet, length: Int) {
        //TODO this could be improved (we already have the packet handler, why calc again later in [PacketSystem]
        world.getSystem(EventSystem::class).dispatch(ClientPacket(session.id, packet, length))
        /*if(handler is GamePacket) {
            handler.entity = if(session.id != -1) world.getEntity(session.id) else null
            handler.es = world.getSystem(EventSystem::class)
            handler.read(session, packet, length)
        } else {
            handler.read(session, packet, length)
        }*/
    }

    override fun disconnect(session: Session) {
        super.disconnect(session)
        if(session.id != -1 && world.entityManager.isActive(session.id)) {
            world.delete(session.id)
        }
    }
}