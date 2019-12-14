package worlds.gregs.hestia.artemis.events

import com.artemis.Entity
import net.mostlyoriginal.api.event.common.EventSystem
import world.gregs.hestia.core.network.codec.message.Message
import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.artemis.getSystem

data class OutBoundMessage(val entity: Int, val message: Message, val close: Boolean = false) : InstantEvent

fun Entity.send(message: Message) {
    world.getSystem(EventSystem::class).dispatch(OutBoundMessage(this.id, message))
}