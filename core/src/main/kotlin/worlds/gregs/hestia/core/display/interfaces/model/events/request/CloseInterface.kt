package worlds.gregs.hestia.core.display.interfaces.model.events.request

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.model.EntityAction

/**
 * Request to close an interface
 * @param id The id of the interface to close
 */
data class CloseInterface(val id: Int): EntityAction(), InstantEvent