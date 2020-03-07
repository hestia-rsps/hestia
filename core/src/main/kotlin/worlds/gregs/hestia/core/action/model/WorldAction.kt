package worlds.gregs.hestia.core.action.model

import com.artemis.World
import net.mostlyoriginal.api.event.common.Cancellable
import net.mostlyoriginal.api.event.common.Event

abstract class WorldAction : WorldActions, Event, Cancellable {
    override lateinit var world: World
    var cancel = false

    override fun setCancelled(value: Boolean) {
        cancel = value
    }

    override fun isCancelled(): Boolean {
        return cancel
    }

}