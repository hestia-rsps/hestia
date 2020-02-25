
package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.CLAN_CHAT_KICK
import worlds.gregs.hestia.network.client.decoders.messages.ClanChatKick

class ClanChatKickDecoder : MessageDecoder<ClanChatKick>(Packet.Type.VAR_BYTE, CLAN_CHAT_KICK) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): ClanChatKick? {
        return ClanChatKick(packet.readBoolean(), packet.readShort(), packet.readString())
    }

}