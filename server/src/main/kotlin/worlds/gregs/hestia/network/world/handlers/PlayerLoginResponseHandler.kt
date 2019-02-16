package worlds.gregs.hestia.network.world.handlers

import io.netty.channel.ChannelHandlerContext
import org.slf4j.LoggerFactory
import world.gregs.hestia.core.network.client.Response
import world.gregs.hestia.core.network.clientRespond
import world.gregs.hestia.core.network.codec.message.MessageHandler
import world.gregs.hestia.core.network.protocol.messages.PlayerLoginResponse

class PlayerLoginResponseHandler(private val sessions: HashMap<Int, ChannelHandlerContext>) : MessageHandler<PlayerLoginResponse> {

    private val logger = LoggerFactory.getLogger(PlayerLoginResponseHandler::class.java)!!

    override fun handle(ctx: ChannelHandlerContext, message: PlayerLoginResponse) {
        val (session, resp) = message
        val playerSession = sessions[session]
        val response = Response.values()[resp]
        if (playerSession == null) {
            logger.info("Error finding session $session $response")
            return
        }
        playerSession.clientRespond(response)
        sessions.remove(session)
    }

}