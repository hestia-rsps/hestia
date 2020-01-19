package worlds.gregs.hestia.core.display.interfaces.model.events

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.model.EntityAction

/**
 * Notification than an interface was closed
 */
data class InterfaceOpened(val id: Int) : EntityAction(), InstantEvent