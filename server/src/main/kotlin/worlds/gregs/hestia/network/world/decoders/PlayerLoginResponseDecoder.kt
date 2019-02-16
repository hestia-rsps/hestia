package worlds.gregs.hestia.network.world.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.WorldOpcodes.LOGIN_RESPONSE
import world.gregs.hestia.core.network.protocol.messages.PlayerLoginResponse

class PlayerLoginResponseDecoder : MessageDecoder<PlayerLoginResponse>(3, LOGIN_RESPONSE) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): PlayerLoginResponse? {
        return PlayerLoginResponse(packet.readShort(), packet.readByte())
    }

}