package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.MOVE_MOUSE
import worlds.gregs.hestia.network.client.decoders.messages.MovedMouse

class MovedMouseDecoder : MessageDecoder<MovedMouse>(Packet.Type.VAR_BYTE, MOVE_MOUSE) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): MovedMouse? {
        return MovedMouse()//TODO("https://www.rune-server.ee/runescape-development/rs2-server/informative-threads/167581-flagged-accounts-mouse-detection.html")
    }

}