package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.SCRIPT_4701
import worlds.gregs.hestia.network.client.decoders.messages.UnknownScript

class UnknownScriptDecoder : MessageDecoder<UnknownScript>(Packet.Type.VAR_BYTE, SCRIPT_4701) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): UnknownScript? {
        return UnknownScript(packet.readString())
    }

}