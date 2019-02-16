package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.COLOUR_ID
import worlds.gregs.hestia.network.client.decoders.messages.SkillcapeColour

class SkillcapeColourDecoder : MessageDecoder<SkillcapeColour>(2, COLOUR_ID) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): SkillcapeColour? {
        return SkillcapeColour(packet.readUnsignedShort())
    }

}