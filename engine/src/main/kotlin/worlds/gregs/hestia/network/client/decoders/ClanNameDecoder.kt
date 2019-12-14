package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.CLAN_NAME
import worlds.gregs.hestia.network.client.decoders.messages.ClanName

class ClanNameDecoder : MessageDecoder<ClanName>(Packet.Type.VAR_BYTE, CLAN_NAME) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): ClanName? {
        return ClanName(packet.readString())
    }

}