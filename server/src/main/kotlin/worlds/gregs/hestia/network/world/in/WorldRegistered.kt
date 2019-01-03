package worlds.gregs.hestia.network.world.`in`

import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.packets.InboundPacket
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketOpcode
import world.gregs.hestia.core.network.packets.PacketSize

@PacketSize(1)
@PacketOpcode(11)
class WorldRegistered : InboundPacket {

    override fun read(session: Session, packet: Packet, length: Int): Boolean {
        return true
    }

}