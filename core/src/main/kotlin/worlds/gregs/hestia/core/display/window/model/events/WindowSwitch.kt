package worlds.gregs.hestia.core.display.window.model.events

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.Action

/**
 * Switches two components
 */
data class WindowSwitch(val fromWindow: Int, val fromSlot: Int, val fromType: Int, val toWindow: Int, val toSlot: Int, val toType: Int) : Action(), InstantEvent