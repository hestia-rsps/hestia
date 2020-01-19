package worlds.gregs.hestia.core.display.dialogue.model.type

import kotlinx.coroutines.CancellableContinuation
import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.task.api.Resendable
import worlds.gregs.hestia.core.task.api.TaskSuspension

/**
 * Sends a dialogue prompt for the player and returns the string they enter
 * @param title The title to display
 */
data class StringEntry(val title: String) : EntityAction(), TaskSuspension<String>, Resendable, InstantEvent {

    override lateinit var continuation: CancellableContinuation<String>
}