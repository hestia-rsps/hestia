package worlds.gregs.hestia.core.entity.entity.model.events

import net.mostlyoriginal.api.event.common.Event

data class Graphic(val entityId: Int, val id: Int, val delay: Int = 0, val height: Int = 0, val rotation: Int = 0, val forceRefresh: Boolean = false) : Event