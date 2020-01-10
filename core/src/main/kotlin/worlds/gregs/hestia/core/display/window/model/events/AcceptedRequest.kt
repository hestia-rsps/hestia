package worlds.gregs.hestia.core.display.window.model.events

import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.display.window.model.Request

/**
 * Context menu request response
 * @param target The target player
 * @param request The selected request
 */
data class AcceptedRequest(val target: Int, val request: Request) : EntityAction()