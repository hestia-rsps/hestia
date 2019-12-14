package worlds.gregs.hestia.network.client.handlers

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageHandler
import worlds.gregs.hestia.GameServer.Companion.worldSession
import worlds.gregs.hestia.network.client.ClientHandshake
import worlds.gregs.hestia.network.client.decoders.messages.GameLogin
import worlds.gregs.hestia.network.world.encoders.messages.PlayerLoginRequest
import java.util.concurrent.atomic.AtomicInteger

class GameLoginHandler(private val sessions: HashMap<Int, ChannelHandlerContext>) : MessageHandler<GameLogin> {

    private val indices = AtomicInteger(1)

    override fun handle(ctx: ChannelHandlerContext, message: GameLogin) {
        val (packet) = message
        val index = indices.getAndIncrement()
        sessions[index] = ctx
        worldSession?.write(PlayerLoginRequest(index, packet), true)
        ctx.pipeline().get(ClientHandshake::class.java).shake(ctx)
    }

}