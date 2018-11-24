package worlds.gregs.hestia.game.events

import com.artemis.Entity
import worlds.gregs.hestia.services.getSystem
import net.mostlyoriginal.api.event.common.Event
import net.mostlyoriginal.api.event.common.EventSystem

data class Animate(val entityId: Int, val id: Int, val speed: Int = 0): Event

fun Entity.animate(id: Int, speed: Int = 0) {
    val eventSystem = world.getSystem(EventSystem::class)
    eventSystem.dispatch(Animate(this.id, id, speed))
}