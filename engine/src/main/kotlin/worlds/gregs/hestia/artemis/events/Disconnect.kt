package worlds.gregs.hestia.artemis.events

import com.artemis.Entity
import net.mostlyoriginal.api.event.common.Event
import worlds.gregs.hestia.artemis.InstantEvent

class Disconnect(val entityId: Int) : Event, InstantEvent {
    constructor(entity: Entity) : this(entity.id)
}