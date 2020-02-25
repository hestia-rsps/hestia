package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.WORLD_MAP_CLICK
import worlds.gregs.hestia.network.client.decoders.messages.WorldMapClose

class WorldMapOpenDecoder : MessageDecoder<WorldMapClose>(4, WORLD_MAP_CLICK) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): WorldMapClose? {
        return WorldMapClose()
    }

}