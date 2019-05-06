package worlds.gregs.hestia.artemis.events

import com.artemis.Entity
import net.mostlyoriginal.api.event.common.Event

class Disconnect(val entityId: Int) : Event {
    constructor(entity: Entity) : this(entity.id)
}