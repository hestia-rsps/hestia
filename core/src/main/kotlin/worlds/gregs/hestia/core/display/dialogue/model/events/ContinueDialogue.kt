package worlds.gregs.hestia.core.display.dialogue.model.events

import net.mostlyoriginal.api.event.common.Cancellable
import net.mostlyoriginal.api.event.common.Event

/**
 * Event notifying the continuation of a dialogue chain
 * @param entityId The entity calling the action
 * @param window The window id
 * @param option The selection option
 * @param widget The selected widget
 */
data class ContinueDialogue(val entityId: Int, val window: Int, val option: Int, val widget: Int) : Event, Cancellable {

    private var cancelled = false

    override fun setCancelled(value: Boolean) {
        cancelled = value
    }

    override fun isCancelled() = cancelled

}