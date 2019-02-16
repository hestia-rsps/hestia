package worlds.gregs.hestia.network.game.decoders

import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.codec.packet.PacketReader
import world.gregs.hestia.core.network.protocol.ClientOpcodes.GAME_LOGIN
import worlds.gregs.hestia.network.game.decoders.messages.GameLogin

class GameLoginDecoder : MessageDecoder<GameLogin>(Packet.Type.VAR_SHORT, GAME_LOGIN) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): GameLogin? {
        return GameLogin(PacketReader(buffer = Unpooled.copiedBuffer(packet.buffer)))
    }

}