package worlds.gregs.hestia.network.world.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.WorldOpcodes.SOCIAL_SERVER_DETAILS
import world.gregs.hestia.core.network.protocol.messages.SocialDetails

class SocialDetailsDecoder(private val decoders: ArrayList<Triple<Int, Int, Boolean>>) : MessageDecoder<SocialDetails>(Packet.Type.VAR_SHORT, SOCIAL_SERVER_DETAILS) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): SocialDetails? {
        val world = packet.readByte()

        repeat(packet.readShort()) {
            decoders.add(Triple(packet.readByte(), packet.readByte(), false))
        }

        repeat(packet.readShort()) {
            decoders.add(Triple(packet.readByte(), packet.readByte(), true))
        }

        return SocialDetails(world)
    }

}