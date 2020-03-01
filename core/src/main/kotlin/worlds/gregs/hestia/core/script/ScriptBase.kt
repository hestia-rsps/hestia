package worlds.gregs.hestia.core.script

import worlds.gregs.hestia.core.action.model.Action
import worlds.gregs.hestia.core.script.dsl.artemis.*
import worlds.gregs.hestia.game.plugin.ScriptLoader.listeners

inline fun <reified E : Action> on(priority: Int = 0, skipCancelledEvents: Boolean = true, noinline conditional: (E.() -> Boolean)? = null, noinline action: (E.() -> Unit)? = null, setup: EventListenerBuilder<E>.() -> Unit = {}) {
    val builder = EventListenerBuilder(E::class, priority, skipCancelledEvents, conditional, action)
    builder.setup()
    listeners += builder.build() ?: return
}