package world.gregs.hestia.network

import org.slf4j.LoggerFactory
import world.gregs.hestia.core.WorldDetails
import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.codec.inbound.PacketInboundHandler
import world.gregs.hestia.core.network.packets.InboundPacket
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.services.load.PacketMap
import world.gregs.hestia.network.world.out.WorldInfo

class LoginServerInboundHandler(private val info: WorldDetails, private val listener: WorldChangeListener? = null, packets: PacketMap) : PacketInboundHandler(packets) {

    override fun connect(session: Session) {
        session.write(WorldInfo(info))
    }

    override fun disconnect(session: Session) {
        listener?.disconnect(session.id)
    }

    override fun process(session: Session, handler: InboundPacket, packet: Packet, length: Int) {
        when(packet.opcode) {
            11 -> {
                val id = packet.readByte()
                if(info.id != -1 && id != info.id) {
                    logger.warn("World id mismatch $id ${info.id}")
                    session.close()
                    return
                }

                info.id = id
                logger.info("Registered as World $id")
                listener?.init(id)
            }
            else -> {
                handler.read(session, packet, length)
            }
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(LoginServerInboundHandler::class.java)
    }
}