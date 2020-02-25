package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.io.Endian
import world.gregs.hestia.io.Modifier
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.WALK
import worlds.gregs.hestia.network.client.decoders.messages.WalkMap

class WalkMapDecoder : MessageDecoder<WalkMap>(5, WALK) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): WalkMap? {
        return WalkMap(packet.readShort(Modifier.ADD, Endian.LITTLE), packet.readShort(Modifier.ADD, Endian.LITTLE), packet.readBoolean())
    }

}