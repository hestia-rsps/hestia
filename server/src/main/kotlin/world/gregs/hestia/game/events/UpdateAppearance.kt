package world.gregs.hestia.game.events

import com.artemis.Entity
import world.gregs.hestia.services.getSystem
import net.mostlyoriginal.api.event.common.Event
import net.mostlyoriginal.api.event.common.EventSystem

data class UpdateAppearance(val entityId: Int): Event

fun Entity.updateAppearance() {
    val eventSystem = world.getSystem(EventSystem::class)
    eventSystem.dispatch(UpdateAppearance(this.id))
}