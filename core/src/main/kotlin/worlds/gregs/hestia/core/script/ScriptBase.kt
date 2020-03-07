package worlds.gregs.hestia.core.script

import net.mostlyoriginal.api.event.common.Event
import worlds.gregs.hestia.core.action.model.Action
import worlds.gregs.hestia.core.script.dsl.artemis.*
import worlds.gregs.hestia.game.plugin.ScriptLoader.listeners

inline fun <reified E : Event> on(priority: Int = 0, skipCancelledEvents: Boolean = true, setup: EventListenerBuilder<E>.() -> Unit = {}) {
    val builder = EventListenerBuilder(E::class, priority, skipCancelledEvents, null, null)
    builder.setup()
    listeners += builder.build() ?: return
}