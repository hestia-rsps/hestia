package worlds.gregs.hestia.network.social

import org.slf4j.LoggerFactory
import world.gregs.hestia.core.network.codec.decode.PacketDecoder
import world.gregs.hestia.core.network.packets.InboundPacket
import world.gregs.hestia.core.services.load.PacketMap

class SocialPacketDecoder : PacketDecoder() {

    override val logger = LoggerFactory.getLogger(SocialPacketDecoder::class.java)!!

    override fun getSize(opcode: Int): Int? {
        return packets.getSize(opcode)
    }

    companion object {
        lateinit var packets: PacketMap<InboundPacket>
    }

}