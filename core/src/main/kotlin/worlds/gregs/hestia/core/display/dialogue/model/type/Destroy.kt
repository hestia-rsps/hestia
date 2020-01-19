package worlds.gregs.hestia.core.display.dialogue.model.type

import kotlinx.coroutines.CancellableContinuation
import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.task.api.TaskSuspension

/**
 * Shows the confirm destruction interface and returns whether the player accepted or cancelled
 * @param text The text to display (can be multi line using <br>)
 * @param item The item id to display
 */
data class Destroy(val text: String, val item: Int) : EntityAction(), TaskSuspension<Boolean>, InstantEvent {
    override lateinit var continuation: CancellableContinuation<Boolean>
}