package worlds.gregs.hestia.core.display.dialogue.model.type

import kotlinx.coroutines.CancellableContinuation
import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.task.api.Resendable
import worlds.gregs.hestia.core.task.api.TaskSuspension

/**
 * Sends a dialogue with item model and text
 * @param text The text to send (can be <br> multiline)
 * @param model The item type to send
 * @param zoom The model zoom
 * @param sprite The backing sprite to send (default null)
 */
data class ItemBox(val text: String, val model: Int, val zoom: Int, val sprite: Int? = null) : EntityAction(), TaskSuspension<Unit>, Resendable, InstantEvent {
    override lateinit var continuation: CancellableContinuation<Unit>
}