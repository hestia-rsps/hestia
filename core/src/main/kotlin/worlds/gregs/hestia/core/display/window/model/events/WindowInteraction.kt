package worlds.gregs.hestia.core.display.window.model.events

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.Action

/**
 * A client interaction with a window widget
 * @param target The targeted window id
 * @param widget The targeted window widget
 * @param first First parameter
 * @param second Second parameter
 * @param option Option parameter
 */
data class WindowInteraction(val target: Int, val widget: Int, val first: Int, val second: Int, val option: Int) : Action(), InstantEvent