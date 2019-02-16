package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.RESUME_PLAYER_OBJ_DIALOGUE
import worlds.gregs.hestia.network.client.decoders.messages.ResumeObjDialogue

class ResumeObjDialogueDecoder : MessageDecoder<ResumeObjDialogue>(2, RESUME_PLAYER_OBJ_DIALOGUE) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): ResumeObjDialogue? {
        return ResumeObjDialogue(packet.readShort())
    }

}