package worlds.gregs.hestia.network.game

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.HandshakeCodec
import world.gregs.hestia.core.network.codec.message.MessageHandshake
import world.gregs.hestia.core.network.codec.message.SimpleMessageHandshakeDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.getSession
import world.gregs.hestia.core.network.protocol.messages.ClientPacket
import worlds.gregs.hestia.GameServer.Companion.worldSession

/**
 * Handles decoding packets to messages and redirecting messages to social server
 */
class GameMessageDecoder(private val socialCodec: List<Triple<Int, Int, Boolean>>, codec: HandshakeCodec, handshake: MessageHandshake) : SimpleMessageHandshakeDecoder(codec, handshake) {

    @Suppress("UNCHECKED_CAST")
    override fun decode(ctx: ChannelHandlerContext, msg: Packet, out: MutableList<Any>) {
        val handshake = handshake.shook(ctx)
        //Main codec takes priority
        if(codec.get(msg.opcode, handshake) != null || socialSize(msg.opcode, handshake) == null) {
            super.decode(ctx, msg, out)
            return
        }

        msg.retain()
        //If social decoder exists write message to social server
        worldSession?.write(ClientPacket(ctx.getSession().id, msg, handshake))
        msg.release()
    }

    override fun channelReadComplete(ctx: ChannelHandlerContext?) {
        worldSession?.flush()
        ctx?.flush()
    }

    private fun socialSize(opcode: Int, handshake: Boolean): Int? {
        return socialCodec.firstOrNull { it.first == opcode && it.third == handshake }?.second
    }
}