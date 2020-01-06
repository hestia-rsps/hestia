package worlds.gregs.hestia.core.display.window.model.events

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.Action

/**
 * @param silent If silent then no client packets should be sent
 */
data class WindowClosed(val target: Int, val silent: Boolean) : Action(), InstantEvent