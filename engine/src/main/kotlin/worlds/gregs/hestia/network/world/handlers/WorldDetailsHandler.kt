package worlds.gregs.hestia.network.world.handlers

import io.netty.channel.ChannelHandlerContext
import org.slf4j.LoggerFactory
import world.gregs.hestia.core.network.codec.message.MessageHandler
import world.gregs.hestia.core.network.getSession
import world.gregs.hestia.core.network.protocol.Details
import world.gregs.hestia.core.network.protocol.messages.SocialDetails
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.artemis.events.SocialLogin
import worlds.gregs.hestia.artemis.forEach
import worlds.gregs.hestia.artemis.players

class WorldDetailsHandler(private val details: Details) : MessageHandler<SocialDetails> {

    private val logger = LoggerFactory.getLogger(WorldDetailsHandler::class.java)!!

    override fun handle(ctx: ChannelHandlerContext, message: SocialDetails) {
        val (world) = message

        if (details.id != -1 && world != details.id) {
            logger.warn("World id mismatch $world ${details.id}")
            ctx.close()
            return
        }

        details.id = world
        logger.info("Registered as World $world")
        GameServer.worldSession = ctx.getSession()
        GameServer.server.init(world)

        //Notify social server of all the current players
        GameServer.server.server?.players()?.forEach {
            GameServer.eventSystem.dispatch(SocialLogin(it))
        }
    }

}