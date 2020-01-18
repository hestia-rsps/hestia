package worlds.gregs.hestia.core.display.interfaces.model.events

import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.display.interfaces.model.PlayerOptions

/**
 * Context menu option
 * @param target The target player
 * @param option The selected option
 */
data class PlayerOption(val target: Int, val option: PlayerOptions) : EntityAction()