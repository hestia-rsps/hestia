package worlds.gregs.hestia.network.world.handlers

import io.netty.channel.ChannelHandlerContext
import org.slf4j.LoggerFactory
import world.gregs.hestia.core.network.codec.message.MessageHandler
import world.gregs.hestia.core.network.getSession
import world.gregs.hestia.core.network.protocol.messages.PlayerLoginSuccess
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.game.events.CreatePlayer

class PlayerLoginSuccessHandler(private val sessions: HashMap<Int, ChannelHandlerContext>) : MessageHandler<PlayerLoginSuccess> {

    private val logger = LoggerFactory.getLogger(PlayerLoginSuccessHandler::class.java)!!

    override fun handle(ctx: ChannelHandlerContext, message: PlayerLoginSuccess) {
        val (session, name, mode, width, height) = message
        val playerSession = sessions[session]
        if (playerSession == null) {
            logger.info("Error finding session $session $name")
            return
        }
        GameServer.eventSystem.dispatch(CreatePlayer(playerSession.getSession(), name, mode, width, height))
        sessions.remove(session)
    }

}