package worlds.gregs.hestia.core.display.request.model.events

import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.display.request.model.Request

/**
 * Context menu request response
 * @param target The target player
 * @param request The selected request
 */
data class RequestResponse(val target: Int, val request: Request) : EntityAction()