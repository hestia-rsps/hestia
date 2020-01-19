package worlds.gregs.hestia.core.display.dialogue.model.type

import kotlinx.coroutines.CancellableContinuation
import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.task.api.Resendable
import worlds.gregs.hestia.core.task.api.TaskSuspension

/**
 * Sends a dialogue prompt for the player and returns the integer they enter
 * @param title The title to display
 */
data class IntEntry(val title: String) : EntityAction(), TaskSuspension<Int>, Resendable, InstantEvent {

    override lateinit var continuation: CancellableContinuation<Int>
}