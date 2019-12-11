package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.DIALOGUE_CONTINUE
import worlds.gregs.hestia.network.client.decoders.messages.DialogueContinue

class DialogueContinueDecoder : MessageDecoder<DialogueContinue>(6, DIALOGUE_CONTINUE) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): DialogueContinue? {
        return DialogueContinue(packet.readInt(Modifier.INVERSE, Endian.MIDDLE), packet.readShort(Modifier.ADD, Endian.LITTLE))
    }

}