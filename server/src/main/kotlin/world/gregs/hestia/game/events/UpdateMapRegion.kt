package world.gregs.hestia.game.events

import com.artemis.Entity
import world.gregs.hestia.services.getSystem
import net.mostlyoriginal.api.event.common.Event
import net.mostlyoriginal.api.event.common.EventSystem

data class UpdateMapRegion(val entityId: Int, val local: Boolean): Event

fun Entity.updateMapRegion(local: Boolean) {
    val eventSystem = world.getSystem(EventSystem::class)
    eventSystem.dispatch(UpdateMapRegion(this.id, local))
}