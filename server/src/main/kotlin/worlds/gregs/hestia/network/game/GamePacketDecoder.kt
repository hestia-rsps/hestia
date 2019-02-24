package worlds.gregs.hestia.network.game

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.HandshakeCodec
import world.gregs.hestia.core.network.codec.decode.SimplePacketHandshakeDecoder
import world.gregs.hestia.core.network.codec.message.MessageHandshake

/**
 * Handles sizes of packets being redirected to social server
 */
class GamePacketDecoder(private val socialCodec: List<Triple<Int, Int, Boolean>>, codec: HandshakeCodec, handshake: MessageHandshake) : SimplePacketHandshakeDecoder(codec, handshake) {

    override fun getSize(ctx: ChannelHandlerContext, opcode: Int): Int? {
        return socialSize(opcode, handshake.shook(ctx)) ?: super.getSize(ctx, opcode)
    }

    private fun socialSize(opcode: Int, handshake: Boolean): Int? {
        return socialCodec.firstOrNull { it.first == opcode && it.third == handshake }?.second
    }
}