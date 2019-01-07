package worlds.gregs.hestia.network.game

import io.netty.buffer.ByteBuf
import org.slf4j.LoggerFactory
import world.gregs.hestia.core.network.codec.decode.PacketDecoder
import worlds.gregs.hestia.GameServer.Companion.socialPackets
import worlds.gregs.hestia.game.PacketHandlerSystem

class GamePacketDecoder : PacketDecoder() {

    override val logger = LoggerFactory.getLogger(GamePacketDecoder::class.java)!!

    override fun getSize(opcode: Int): Int? {
        return socialPackets.getOrDefault(opcode, PacketHandlerSystem.gamePackets.getSize(opcode))
    }

    override fun missingSize(buf: ByteBuf, opcode: Int, out: MutableList<Any>) {
        //Clears buffer to stop accumulation
        if(opcode != 16) {
            logger.warn("Unhandled packet: $opcode")
        }
        buf.skipBytes(buf.readableBytes())
        out.add(0)
    }
}