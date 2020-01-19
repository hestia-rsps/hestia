package worlds.gregs.hestia.core.display.interfaces.model.events

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.model.EntityAction

/**
 * Switches two components
 */
data class ComponentSwitch(val fromWindow: Int, val fromSlot: Int, val fromType: Int, val toWindow: Int, val toSlot: Int, val toType: Int) : EntityAction(), InstantEvent