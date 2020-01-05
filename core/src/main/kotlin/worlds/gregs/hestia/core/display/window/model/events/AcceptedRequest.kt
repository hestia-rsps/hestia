package worlds.gregs.hestia.core.display.window.model.events

import worlds.gregs.hestia.core.display.window.model.Request
import worlds.gregs.hestia.core.task.api.event.TargetEvent

/**
 * Context menu request response
 * @param entity The entity doing the action
 * @param target The target player
 * @param request The selected request
 */
data class AcceptedRequest(override val entity: Int, override val target: Int, val request: Request) : TargetEvent