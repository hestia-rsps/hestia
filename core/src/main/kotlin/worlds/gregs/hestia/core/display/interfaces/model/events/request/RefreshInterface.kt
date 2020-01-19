package worlds.gregs.hestia.core.display.interfaces.model.events.request

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.model.EntityAction

/**
 * Request to refresh interface
 * @param id The interface id to refresh
 */
data class RefreshInterface(val id: Int): EntityAction(), InstantEvent