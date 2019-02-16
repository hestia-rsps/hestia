package worlds.gregs.hestia.game.events

import com.artemis.Entity
import net.mostlyoriginal.api.event.common.Event
import net.mostlyoriginal.api.event.common.EventSystem
import world.gregs.hestia.core.network.codec.message.Message
import worlds.gregs.hestia.services.getSystem

data class OutBoundMessage(val entity: Int, val message: Message, val close: Boolean = false) : Event

fun Entity.send(message: Message) {
    world.getSystem(EventSystem::class).dispatch(OutBoundMessage(this.id, message))
}