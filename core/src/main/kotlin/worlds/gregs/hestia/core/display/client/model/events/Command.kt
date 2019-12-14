package worlds.gregs.hestia.core.display.client.model.events

import net.mostlyoriginal.api.event.common.Cancellable
import worlds.gregs.hestia.core.task.api.event.EntityEvent

data class Command(override val entity: Int, val prefix: String, val content: String) : EntityEvent, Cancellable {

    private var cancelled = false

    override fun setCancelled(value: Boolean) {
        cancelled = value
    }

    override fun isCancelled() = cancelled

}