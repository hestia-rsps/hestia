package worlds.gregs.hestia.artemis.events

import net.mostlyoriginal.api.event.common.Event

class Hit(val entityId: Int, val amount: Int, val mark: Int, val delay: Int = 0, var critical: Boolean = false, val source: Int = -1, val soak: Int = -1) : Event