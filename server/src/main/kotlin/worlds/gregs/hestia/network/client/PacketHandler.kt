package worlds.gregs.hestia.network.client

import com.artemis.BaseSystem
import io.netty.channel.ChannelHandlerContext
import org.slf4j.LoggerFactory
import world.gregs.hestia.core.network.codec.Dispatcher
import world.gregs.hestia.core.network.codec.HandshakeDispatcher
import world.gregs.hestia.core.network.codec.MessageDispatcher
import world.gregs.hestia.core.network.codec.message.Message
import world.gregs.hestia.core.network.codec.message.MessageHandler
import world.gregs.hestia.core.network.getSession
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.reflect.KClass

open class PacketHandler : BaseSystem(), Dispatcher, HandshakeDispatcher {
    private val others = HashMap<KClass<*>, ClientMessageHandler<*>>()
    private val logger = LoggerFactory.getLogger(PacketHandler::class.java)!!
    private val queue = ConcurrentLinkedQueue<Pair<Int, Message>>()

    /*
    TODO some kind of system where we can use the new annotation events
        But also event system is custom so events queued and only sent at the beginning of a tick
        Then change removals to events so disconnects happen at the start
     */
    /*
        Client Message queuing
     */

    inline fun <reified T : Message> bind(encoder: ClientMessageHandler<T>) {
        bind(T::class, encoder)
    }

    fun <T : Message> bind(type: KClass<T>, handler: ClientMessageHandler<T>) {
        others[type] = handler
    }

    private fun <T : Message> getClient(type: KClass<T>): ClientMessageHandler<T>? {
        return others[type] as? ClientMessageHandler<T>
    }

    override fun processSystem() {
        var pair: Pair<Int, Message>? = queue.poll()
        while(pair != null) {

            val handler = getClient(pair.second::class) as? ClientMessageHandler<Message>
            if(handler != null) {
                handler.handle(pair.first, pair.second)
            } else {
                logger.warn("Unhandled message: ${pair.second}")
            }

            pair = queue.poll()
        }
    }

    override fun dispatch(ctx: ChannelHandlerContext, message: Message) {
        val handler = get(message::class)

        if (handler != null) {
            try {
                (handler as? MessageHandler<Message>)?.handle(ctx, message)
            } catch (t: Throwable) {
                logger.warn("Error processing message: ", t)
            }
        } else {
            val handler = getClient(message::class)
            if(handler != null) {
                queue.add(Pair(ctx.getSession().id, message))
            } else {
                logger.warn("Unhandled message dispatch: $message")
            }
        }
    }


    /*
        Handlers
     */
    private val handlers = HashMap<KClass<*>, MessageHandler<*>>()

    override fun <T : Message> bind(type: KClass<T>, handler: MessageHandler<T>) {
        handlers[type] = handler
    }

    inline fun <reified T : Message> bind(encoder: MessageHandler<T>) {
        bind(T::class, encoder)
    }

    override fun <T : Message> get(type: KClass<T>): MessageHandler<T>? {
        return handlers[type] as? MessageHandler<T>
    }

    /*
        Handshake
     */
    private val handshakeDispatcher = MessageDispatcher()

    override fun <T : Message> bind(type: KClass<T>, handler: MessageHandler<T>, handshake: Boolean) {
        return if (handshake) handshakeDispatcher.bind(type, handler) else bind(type, handler)
    }

    inline fun <reified T : Message> bind(encoder: MessageHandler<T>, handshake: Boolean) {
        bind(T::class, encoder, handshake)
    }

    override fun <T : Message> get(type: KClass<T>, handshake: Boolean): MessageHandler<T>? {
        return if (handshake) get(type) else handshakeDispatcher.get(type)
    }

    override fun dispatch(ctx: ChannelHandlerContext, message: Message, handshake: Boolean) {
        if (handshake) dispatch(ctx, message) else handshakeDispatcher.dispatch(ctx, message)
    }

}