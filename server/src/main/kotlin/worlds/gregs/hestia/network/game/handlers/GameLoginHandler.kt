package worlds.gregs.hestia.network.game.handlers

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageHandler
import worlds.gregs.hestia.GameServer.Companion.worldSession
import worlds.gregs.hestia.network.game.GameHandshake
import worlds.gregs.hestia.network.game.decoders.messages.GameLogin
import worlds.gregs.hestia.network.world.encoders.messages.PlayerLoginRequest

class GameLoginHandler(private val sessions: HashMap<Int, ChannelHandlerContext>) : MessageHandler<GameLogin> {

    override fun handle(ctx: ChannelHandlerContext, message: GameLogin) {
        val (packet) = message
        val index = (1..Short.MAX_VALUE)
                .firstOrNull { it > 0 && !sessions.containsKey(it) } ?: throw IllegalStateException("Maximum player login session reached")
        sessions[index] = ctx
        worldSession?.write(PlayerLoginRequest(index, packet), true)
        ctx.pipeline().get(GameHandshake::class.java).shake(ctx)
    }

}