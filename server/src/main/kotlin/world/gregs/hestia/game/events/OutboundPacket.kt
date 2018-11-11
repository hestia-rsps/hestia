package world.gregs.hestia.game.events

import com.artemis.Entity
import net.mostlyoriginal.api.event.common.Event
import net.mostlyoriginal.api.event.common.EventSystem
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.services.getSystem

data class OutBoundPacket(val entity: Int, val packet: Packet, val close: Boolean = false) : Event {
    constructor(entity: Int, packet: Packet.Builder, close: Boolean = false) : this(entity, packet.build(), close)
}

fun Entity.send(builder: Packet.Builder) {
    val eventSystem = world.getSystem(EventSystem::class)
    eventSystem.dispatch(OutBoundPacket(this.id, builder.build()))
}