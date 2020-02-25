package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.DONE_LOADING_REGION
import worlds.gregs.hestia.network.client.decoders.messages.RegionLoaded

class RegionLoadedDecoder : MessageDecoder<RegionLoaded>(0, DONE_LOADING_REGION) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): RegionLoaded? {
        return RegionLoaded()
    }

}