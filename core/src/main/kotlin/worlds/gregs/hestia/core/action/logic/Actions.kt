package worlds.gregs.hestia.core.action.logic

import com.artemis.World
import net.mostlyoriginal.api.event.common.Event
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.artemis.getSystem

infix fun World.dispatch(event: Event) {
    getSystem(EventSystem::class).dispatch(event)
}