package worlds.gregs.hestia.core.task.model.events

import net.mostlyoriginal.api.event.common.Cancellable
import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.task.api.TaskType

/**
 * Event for systems to handle a [TaskType] if it's applicable to them.
 */
data class ProcessTaskSuspension(val entityId: Int, val type: TaskType<*>) : InstantEvent, Cancellable {

    private var cancelled = false

    override fun setCancelled(value: Boolean) {
        cancelled = value
    }

    override fun isCancelled() = cancelled
}