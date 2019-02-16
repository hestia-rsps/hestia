package worlds.gregs.hestia.game

import io.netty.channel.ChannelHandlerContext
import net.mostlyoriginal.api.system.core.PassiveSystem
import world.gregs.hestia.core.network.codec.message.Message
import world.gregs.hestia.core.network.codec.message.MessageHandler
import world.gregs.hestia.core.network.getSession

/**
 * Template for systems handling inbound [Message]s
 */
abstract class MessageHandlerSystem<T : Message> : PassiveSystem(), MessageHandler<T> {

    override fun handle(ctx: ChannelHandlerContext, message: T) {
        handle(ctx.getSession().id, message)
    }

    /**
     * Handles incoming packet
     * @param entityId The entity of the client whom sent the packet
     * @param message The [Message] received
     */
    abstract fun handle(entityId: Int, message: T)
}