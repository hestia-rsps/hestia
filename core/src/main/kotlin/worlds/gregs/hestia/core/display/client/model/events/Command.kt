package worlds.gregs.hestia.core.display.client.model.events

import net.mostlyoriginal.api.event.common.Event

data class Command(val entityId: Int, val prefix: String, val command: String = prefix) : Event