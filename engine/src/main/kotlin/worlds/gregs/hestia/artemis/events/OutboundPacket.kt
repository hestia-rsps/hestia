package worlds.gregs.hestia.artemis.events

import com.artemis.Entity
import net.mostlyoriginal.api.event.common.EventSystem
import world.gregs.hestia.core.network.codec.packet.Packet
import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.service.getSystem

data class OutBoundPacket(val entity: Int, val packet: Packet, val close: Boolean = false) : InstantEvent

fun Entity.send(packet: Packet) {
    world.getSystem(EventSystem::class).dispatch(OutBoundPacket(this.id, packet))
}