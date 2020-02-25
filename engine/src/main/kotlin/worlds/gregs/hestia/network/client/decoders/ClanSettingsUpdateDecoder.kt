package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.CLAN_SETTINGS_UPDATE
import worlds.gregs.hestia.network.client.decoders.messages.ClanSettingsUpdate

class ClanSettingsUpdateDecoder : MessageDecoder<ClanSettingsUpdate>(Packet.Type.VAR_BYTE, CLAN_SETTINGS_UPDATE) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): ClanSettingsUpdate? {
        return ClanSettingsUpdate(packet.readShort(), packet.readString())
    }

}