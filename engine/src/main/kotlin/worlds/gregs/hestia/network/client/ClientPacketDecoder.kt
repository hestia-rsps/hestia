package worlds.gregs.hestia.network.client

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.cache.crypto.Cipher
import world.gregs.hestia.core.network.codec.HandshakeCodec
import world.gregs.hestia.core.network.codec.decode.SimplePacketHandshakeDecoder
import world.gregs.hestia.core.network.codec.message.MessageHandshake

/**
 * Handles sizes of packets being redirected to social server
 */
class ClientPacketDecoder(private val socialCodec: List<Triple<Int, Int, Boolean>>, codec: HandshakeCodec, handshake: MessageHandshake) : SimplePacketHandshakeDecoder(codec, handshake) {

    var cipher: Cipher? = null

    override fun readOpcode(buf: ByteBuf): Int {
        return if(cipher != null) (buf.readUnsignedByte().toInt() - cipher!!.nextInt()) and 0xff else super.readOpcode(buf)
    }

    override fun getSize(ctx: ChannelHandlerContext, opcode: Int): Int? {
        return socialSize(opcode, handshake.shook(ctx)) ?: super.getSize(ctx, opcode)
    }

    private fun socialSize(opcode: Int, handshake: Boolean): Int? {
        return socialCodec.firstOrNull { it.first == opcode && it.third == handshake }?.second
    }
}