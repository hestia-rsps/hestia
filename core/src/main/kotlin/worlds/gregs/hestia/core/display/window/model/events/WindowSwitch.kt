package worlds.gregs.hestia.core.display.window.model.events

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.task.api.event.EntityEvent

/**
 * Switches two components
 * @param entity The entity doing the interaction
 */
data class WindowSwitch(override val entity: Int, val fromWindow: Int, val fromSlot: Int, val fromType: Int, val toWindow: Int, val toSlot: Int, val toType: Int) : EntityEvent, InstantEvent