package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.CLAN_FORUM_THREAD
import worlds.gregs.hestia.network.client.decoders.messages.ClanForumThread

class ClanForumThreadDecoder : MessageDecoder<ClanForumThread>(Packet.Type.VAR_BYTE, CLAN_FORUM_THREAD) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): ClanForumThread? {
        return ClanForumThread(packet.readString())
    }

}