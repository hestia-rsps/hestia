package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.CUTSCENE_ACTION
import worlds.gregs.hestia.network.client.decoders.messages.CutsceneAction

class CutsceneActionDecoder : MessageDecoder<CutsceneAction>(0, CUTSCENE_ACTION) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): CutsceneAction? {
        return CutsceneAction()
    }

}