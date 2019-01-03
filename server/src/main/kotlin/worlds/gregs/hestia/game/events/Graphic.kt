package worlds.gregs.hestia.game.events

import com.artemis.Entity
import worlds.gregs.hestia.services.getSystem
import net.mostlyoriginal.api.event.common.Event
import net.mostlyoriginal.api.event.common.EventSystem

data class Graphic(val entityId: Int, val id: Int, val delay: Int = 0, val height: Int = 0, val rotation: Int = 0, val forceRefresh: Boolean = false) : Event