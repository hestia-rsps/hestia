package world.gregs.hestia.game.events

import com.artemis.Entity
import world.gregs.hestia.services.getSystem
import net.mostlyoriginal.api.event.common.Event
import net.mostlyoriginal.api.event.common.EventSystem

data class Graphic(val entityId: Int, val id: Int, val delay: Int = 0, val height: Int = 0, val rotation: Int = 0, val forceRefresh: Boolean = false) : Event

fun Entity.graphic(id: Int, delay: Int = 0, height: Int = 0, rotation: Int = 0, refresh: Boolean = false) {
    val eventSystem = world.getSystem(EventSystem::class)
    eventSystem.dispatch(Graphic(this.id, id, delay, height, rotation, refresh))
}