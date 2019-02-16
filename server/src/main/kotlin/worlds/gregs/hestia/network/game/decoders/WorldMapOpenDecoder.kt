package worlds.gregs.hestia.network.game.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.WORLD_MAP_CLICK
import worlds.gregs.hestia.network.game.decoders.messages.WorldMapOpen

class WorldMapOpenDecoder : MessageDecoder<WorldMapOpen>(4, WORLD_MAP_CLICK) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): WorldMapOpen? {
        return WorldMapOpen()
    }

}