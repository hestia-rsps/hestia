package worlds.gregs.hestia.core.display.dialogue.model.type

import kotlinx.coroutines.CancellableContinuation
import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.display.dialogue.model.Expression
import worlds.gregs.hestia.core.task.api.TaskType
import worlds.gregs.hestia.core.task.model.await.Resendable

/**
 * Sends a chat box dialogue with both the players head and [mob] head performing [animation] and [playerAnimation] respectively.
 * @param lines The lines for the player to say
 * @param mob The type id of the mob who's head to display
 * @param animation The animation for the mobs head to perform @see [Expression]
 * @param playerAnimation The animation for the players head to perform @see [Expression]
 */
data class DoubleChat(val lines: List<String>, val mob: Int, val animation: Int, val playerAnimation: Int) : EntityAction(), TaskType<Unit>, Resendable {
    init {
        check(lines.size <= 4) { "Maximum double dialogue lines 4" }
    }

    override lateinit var continuation: CancellableContinuation<Unit>
}