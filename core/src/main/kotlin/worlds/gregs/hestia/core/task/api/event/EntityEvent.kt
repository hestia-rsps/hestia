package worlds.gregs.hestia.core.task.api.event

import net.mostlyoriginal.api.event.common.Event

interface EntityEvent : Event {
    val entity: Int
}