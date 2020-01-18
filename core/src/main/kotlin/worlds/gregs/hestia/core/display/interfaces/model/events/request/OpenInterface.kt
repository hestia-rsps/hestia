package worlds.gregs.hestia.core.display.interfaces.model.events.request

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.model.EntityAction

/**
 * Request to open an interface
 * @param id The interface id to open
 */
data class OpenInterface(val id: Int): EntityAction(), InstantEvent