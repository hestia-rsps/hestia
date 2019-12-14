package worlds.gregs.hestia.core.task.model.events

import net.mostlyoriginal.api.event.common.Cancellable
import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.game.task.DeferralType

/**
 * Event for systems to handle a [DeferralType] if it's applicable to them.
 */
data class ProcessDeferral(val entityId: Int, val deferral: DeferralType) : InstantEvent, Cancellable {

    private var cancelled = false

    override fun setCancelled(value: Boolean) {
        cancelled = value
    }

    override fun isCancelled() = cancelled
}