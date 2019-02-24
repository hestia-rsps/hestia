package worlds.gregs.hestia.game.plugins.client.systems.network

import com.artemis.ComponentMapper
import io.netty.channel.ChannelFutureListener
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.game.events.OutBoundMessage
import worlds.gregs.hestia.game.events.OutBoundPacket
import worlds.gregs.hestia.game.plugins.client.components.NetworkSession

class PacketSender : PassiveSystem() {

    private lateinit var networkSessionMapper: ComponentMapper<NetworkSession>

    @Subscribe
    fun send(event: OutBoundMessage) {
        if(networkSessionMapper.has(event.entity)) {
            val component = networkSessionMapper.get(event.entity)
            if (component != null) {
                val channel = component.channel

                if (channel.isOpen) {
                    synchronized(channel) {
                        val future = channel.writeAndFlush(event.message)
                        if (event.close) {
                            future.addListener(ChannelFutureListener.CLOSE) ?: channel.close()
                        }
                    }
                }
            }
        }
    }

    @Subscribe
    fun send(event: OutBoundPacket) {
        if(networkSessionMapper.has(event.entity)) {
            val component = networkSessionMapper.get(event.entity)
            if (component != null) {
                val channel = component.channel

                if (channel.isOpen) {
                    synchronized(channel) {
                        val future = channel.writeAndFlush(event.packet.buffer)
                        if (event.close) {
                            future.addListener(ChannelFutureListener.CLOSE) ?: channel.close()
                        }
                    }
                }
            }
        }
    }
}