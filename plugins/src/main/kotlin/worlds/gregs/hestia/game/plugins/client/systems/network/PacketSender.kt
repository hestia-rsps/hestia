package worlds.gregs.hestia.game.plugins.client.systems.network

import com.artemis.ComponentMapper
import io.netty.channel.ChannelFutureListener
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import net.mostlyoriginal.api.event.common.Subscribe
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.api.client.components.NetworkSession
import worlds.gregs.hestia.artemis.ParallelSystem
import worlds.gregs.hestia.artemis.events.OutBoundMessage
import worlds.gregs.hestia.artemis.events.OutBoundPacket
import worlds.gregs.hestia.services.Aspect

class PacketSender : ParallelSystem(Aspect.all(NetworkSession::class)) {

    private val logger = LoggerFactory.getLogger(PacketSender::class.java)!!
    private lateinit var networkSessionMapper: ComponentMapper<NetworkSession>

    @Subscribe
    fun send(event: OutBoundMessage) {
        if(networkSessionMapper.has(event.entity)) {
            val component = networkSessionMapper.get(event.entity)
            if (component != null) {
                val channel = component.channel

                if (channel.isOpen) {
                    synchronized(channel) {
                        val future = channel.write(event.message)
                        if (event.close) {
                            logger.info("Close channel $event")
                            future.addListener(ChannelFutureListener.CLOSE) ?: channel.close()
                        }
                    }
                }
            }
        }
    }

    override fun processAsync(entityId: Int) = GlobalScope.async {
        if(networkSessionMapper.has(entityId)) {
            val component = networkSessionMapper.get(entityId)
            if (component != null) {
                val channel = component.channel

                if (channel.isOpen) {
                    synchronized(channel) {
                        channel.flush()
                    }
                }
            }
        }
    }


    @Subscribe
    fun send(event: OutBoundPacket) {
        val (entity, packet) = event
        if(networkSessionMapper.has(entity)) {
            val component = networkSessionMapper.get(entity)
            if (component != null) {
                val channel = component.channel
                if (channel.isOpen) {
                    synchronized(channel) {
                        val future = channel.write(packet.buffer)
                        if (event.close) {
                            logger.info("Close channel $event")
                            future.addListener(ChannelFutureListener.CLOSE) ?: channel.close()
                        }
                    }
                }
            }
        }
    }
}