package worlds.gregs.hestia.core.action.model

import net.mostlyoriginal.api.event.common.Cancellable
import net.mostlyoriginal.api.event.common.Event
import net.mostlyoriginal.api.event.common.EventSystem

abstract class EntityAction : WorldAction(), EntityActions, Event, Cancellable {
    override var entity: Int = -1
}

fun EventSystem.perform(entityId: Int, action: EntityAction) {
    action.entity = entityId
    dispatch(action)
}