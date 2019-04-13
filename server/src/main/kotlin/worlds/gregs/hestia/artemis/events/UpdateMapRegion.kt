package worlds.gregs.hestia.artemis.events

import net.mostlyoriginal.api.event.common.Event

data class UpdateMapRegion(val entityId: Int, val local: Boolean, val forceRefresh: Boolean): Event