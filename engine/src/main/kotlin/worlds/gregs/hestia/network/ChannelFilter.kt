package worlds.gregs.hestia.network

import com.google.common.collect.ConcurrentHashMultiset
import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import world.gregs.hestia.core.network.NetworkConstants
import world.gregs.hestia.core.network.client.Response
import world.gregs.hestia.core.network.clientRespond
import java.net.InetSocketAddress

/**
 * Limits the number of connections per host
 */
@ChannelHandler.Sharable
class ChannelFilter(private val limit: Int) : ChannelInboundHandlerAdapter() {

    private val connections = ConcurrentHashMultiset.create<String>()

    override fun channelRegistered(ctx: ChannelHandlerContext) {
        val host = ctx.getHost() ?: return

        connections.add(host)

        if(connections.count(host) > limit) {
            ctx.clientRespond(Response.LOGIN_LIMIT_EXCEEDED)
            ctx.close()
        } else {
            ctx.fireChannelRegistered()
        }
    }

    override fun channelUnregistered(ctx: ChannelHandlerContext) {
        val host = ctx.getHost() ?: return

        connections.remove(host)
        ctx.fireChannelUnregistered()
    }

    private fun ChannelHandlerContext.getHost(): String? {
        return (channel()?.remoteAddress() as? InetSocketAddress)?.address?.hostAddress ?: NetworkConstants.LOCALHOST
    }
}