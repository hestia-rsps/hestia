package worlds.gregs.hestia.artemis.events

import net.mostlyoriginal.api.event.common.Event

data class Animate(val entityId: Int, val id: Int, val speed: Int = 0): Event