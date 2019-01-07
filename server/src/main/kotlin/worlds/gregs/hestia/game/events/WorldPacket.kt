package worlds.gregs.hestia.game.events

import net.mostlyoriginal.api.event.common.Event
import world.gregs.hestia.core.network.packets.Packet

data class WorldPacket(val entity: Int, val packet: Packet, val close: Boolean = false) : Event {
    constructor(entity: Int, packet: Packet.Builder, close: Boolean = false) : this(entity, packet.build(), close)
}