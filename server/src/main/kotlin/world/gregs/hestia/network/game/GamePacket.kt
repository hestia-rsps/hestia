package world.gregs.hestia.network.game

import com.artemis.Entity
import net.mostlyoriginal.api.event.common.EventSystem
import world.gregs.hestia.core.network.packets.InboundPacket

abstract class GamePacket : InboundPacket {
    lateinit var es: EventSystem
    var entity: Entity? = null
}