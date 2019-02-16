package worlds.gregs.hestia.game.events

import com.artemis.Entity
import net.mostlyoriginal.api.event.common.Event
import net.mostlyoriginal.api.event.common.EventSystem
import world.gregs.hestia.core.network.codec.packet.Packet
import worlds.gregs.hestia.services.getSystem

data class OutBoundPacket(val entity: Int, val packet: Packet, val close: Boolean = false) : Event

fun Entity.send(packet: Packet) {
    world.getSystem(EventSystem::class).dispatch(OutBoundPacket(this.id, packet))
}