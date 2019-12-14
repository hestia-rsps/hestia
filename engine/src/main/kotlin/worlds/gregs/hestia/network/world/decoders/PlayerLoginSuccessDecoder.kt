package worlds.gregs.hestia.network.world.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.WorldOpcodes.LOGIN_SUCCESS
import world.gregs.hestia.core.network.protocol.messages.PlayerLoginSuccess

class PlayerLoginSuccessDecoder : MessageDecoder<PlayerLoginSuccess>(Packet.Type.VAR_BYTE, LOGIN_SUCCESS) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): PlayerLoginSuccess? {
        return PlayerLoginSuccess(packet.readShort(), packet.readString(), (0 until packet.readByte()).map { packet.readInt() }.toIntArray(), packet.readByte(), packet.readUnsignedShort(), packet.readUnsignedShort())
    }

}