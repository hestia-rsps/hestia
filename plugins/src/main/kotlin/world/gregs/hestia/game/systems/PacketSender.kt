package world.gregs.hestia.game.systems

import com.artemis.ComponentMapper
import world.gregs.hestia.game.component.NetworkSession
import world.gregs.hestia.game.events.OutBoundPacket
import io.netty.channel.ChannelFutureListener
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem

class PacketSender : PassiveSystem() {

    private lateinit var networkSessionMapper: ComponentMapper<NetworkSession>

    @Subscribe
    fun send(event: OutBoundPacket) {
        val component = networkSessionMapper.get(event.entity)
        val channel = component.channel

        if(channel.isOpen) {
            synchronized(channel) {
                val future = channel.writeAndFlush(event.packet)
                if(event.close) {
                    future.addListener(ChannelFutureListener.CLOSE) ?: channel.close()
                }
            }
        }
    }
}