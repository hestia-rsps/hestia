package worlds.gregs.hestia.network.client.decoders

import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.packet.PacketReader
import world.gregs.hestia.core.network.protocol.ClientOpcodes.GAME_LOGIN
import worlds.gregs.hestia.network.client.decoders.messages.GameLogin

class GameLoginDecoder : MessageDecoder<GameLogin>(Packet.Type.VAR_SHORT, GAME_LOGIN) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): GameLogin? {
        return GameLogin(PacketReader(buffer = Unpooled.copiedBuffer(packet.buffer)))
    }

}