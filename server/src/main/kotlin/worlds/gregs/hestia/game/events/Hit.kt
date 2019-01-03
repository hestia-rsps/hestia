package worlds.gregs.hestia.game.events

import com.artemis.Entity
import worlds.gregs.hestia.services.getSystem
import net.mostlyoriginal.api.event.common.Event
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.game.update.Marker

class Hit(val entityId: Int, val amount: Int, val mark: Int, val delay: Int = 0, var critical: Boolean = false, val source: Int = -1, val soak: Int = -1) : Event