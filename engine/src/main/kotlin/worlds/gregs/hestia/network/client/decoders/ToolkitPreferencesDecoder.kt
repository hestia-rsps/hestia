package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.TOOLKIT_PREFERENCES
import worlds.gregs.hestia.network.client.decoders.messages.ToolkitPreferences

class ToolkitPreferencesDecoder : MessageDecoder<ToolkitPreferences>(Packet.Type.VAR_BYTE, TOOLKIT_PREFERENCES) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): ToolkitPreferences? {
        packet.readByte()//0
        //TODO
        return ToolkitPreferences()
    }

}