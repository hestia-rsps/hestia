package worlds.gregs.hestia.artemis.events

import net.mostlyoriginal.api.event.common.Cancellable
import net.mostlyoriginal.api.event.common.Event

data class ContinueDialogue(val entityId: Int, val interfaceId: Int, val buttonId: Int, val component: Int) : Event, Cancellable {

    private var cancelled = false

    override fun setCancelled(value: Boolean) {
        cancelled = value
    }

    override fun isCancelled() = cancelled

}