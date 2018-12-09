package worlds.gregs.hestia.game.events

import com.artemis.Entity
import net.mostlyoriginal.api.event.common.Event
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.services.getSystem

data class UpdateMapRegion(val entityId: Int, val local: Boolean, val forceRefresh: Boolean): Event