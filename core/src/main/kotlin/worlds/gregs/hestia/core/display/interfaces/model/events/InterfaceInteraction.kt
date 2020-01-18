package worlds.gregs.hestia.core.display.interfaces.model.events

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.model.EntityAction

/**
 * A client interaction with a interface component
 * @param id The targeted interface id
 * @param component The targeted interface component
 * @param first First parameter
 * @param second Second parameter
 * @param option Option selected
 */
data class InterfaceInteraction(val id: Int, val component: Int, val first: Int, val second: Int, val option: Int) : EntityAction(), InstantEvent