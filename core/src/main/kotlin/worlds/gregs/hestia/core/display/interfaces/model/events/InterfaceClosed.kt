package worlds.gregs.hestia.core.display.interfaces.model.events

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.model.EntityAction

/**
 * @param id The interface id
 */
data class InterfaceClosed(val id: Int) : EntityAction(), InstantEvent