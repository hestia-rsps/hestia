package worlds.gregs.hestia.core.display.dialogue.model.type

import kotlinx.coroutines.CancellableContinuation
import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.display.dialogue.model.Expression
import worlds.gregs.hestia.core.task.api.Resendable
import worlds.gregs.hestia.core.task.api.TaskSuspension

/**
 * Sends a chat box dialogue with both the players head and [npc] head performing [animation] and [playerAnimation] respectively.
 * @param lines The lines for the player to say
 * @param npc The type id of the npc who's head to display
 * @param animation The animation for the npcs head to perform @see [Expression]
 * @param playerAnimation The animation for the players head to perform @see [Expression]
 */
data class DoubleChat(val lines: List<String>, val npc: Int, val animation: Int, val playerAnimation: Int) : EntityAction(), TaskSuspension<Unit>, Resendable, InstantEvent {
    init {
        check(lines.size <= 4) { "Maximum double dialogue lines 4" }
    }

    override lateinit var continuation: CancellableContinuation<Unit>
}