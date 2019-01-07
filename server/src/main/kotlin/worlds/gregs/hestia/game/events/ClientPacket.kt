package worlds.gregs.hestia.game.events

import net.mostlyoriginal.api.event.common.Event
import world.gregs.hestia.core.network.packets.Packet

data class ClientPacket(val entityId: Int, val packet: Packet) : Event