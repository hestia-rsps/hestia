package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.REGION_LOADING
import worlds.gregs.hestia.network.client.decoders.messages.RegionLoading

class RegionLoadingDecoder : MessageDecoder<RegionLoading>(4, REGION_LOADING) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): RegionLoading? {
        //1057001181
        return null//Unknown5()
    }

}