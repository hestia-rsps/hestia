package worlds.gregs.hestia.core.display.interfaces.model.events

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.model.EntityAction

/**
 * @param id The interface id
 * @param silent If silent then no client packets should be sent
 */
data class InterfaceClosed(val id: Int, val silent: Boolean) : EntityAction(), InstantEvent