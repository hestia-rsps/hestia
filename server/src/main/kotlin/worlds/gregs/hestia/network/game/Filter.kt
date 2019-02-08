package worlds.gregs.hestia.network.game

import com.google.common.collect.ConcurrentHashMultiset
import io.netty.channel.Channel
import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import org.slf4j.LoggerFactory
import world.gregs.hestia.core.Settings
import world.gregs.hestia.core.network.NetworkConstants
import world.gregs.hestia.core.network.NetworkConstants.SESSION_KEY
import world.gregs.hestia.core.network.packets.out.Response
import java.net.InetSocketAddress

@ChannelHandler.Sharable
class Filter : ChannelInboundHandlerAdapter() {

    private val connections: ConcurrentHashMultiset<String> = ConcurrentHashMultiset.create<String>()

    override fun channelRegistered(ctx: ChannelHandlerContext?) {
        val session = ctx?.channel()?.attr(SESSION_KEY)?.get()
                ?: throw IllegalStateException("Session is null")

        val host = ctx.channel().getHost()

        if(host.equals(NetworkConstants.LOCALHOST, true)) {
            return
        }

        //Add host
        connections.add(host)

        //Check connection limit
        if(connections.count(host) > Settings.getInt("connectionLimit", 1)) {
            session.respond(Response.LOGIN_LIMIT_EXCEEDED)
            logger.info("Connection limit exceeded $host")
            return
        }

        //Continue as normal
        ctx.fireChannelRegistered()
    }

    override fun channelUnregistered(ctx: ChannelHandlerContext?) {
        val host = ctx?.channel().getHost()

        if(host.equals(NetworkConstants.LOCALHOST, true)) {
            return
        }

        //Remove host
        connections.remove(host)

        //Continue as normal
        ctx?.fireChannelUnregistered()
    }

    companion object {
        private val logger = LoggerFactory.getLogger(Filter::class.java)
    }
}

fun Channel?.getHost(): String {
    return (this?.remoteAddress() as? InetSocketAddress)?.address?.hostAddress ?: NetworkConstants.LOCALHOST
}