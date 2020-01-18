package worlds.gregs.hestia.core.display.dialogue.model.type

import kotlinx.coroutines.CancellableContinuation
import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.display.dialogue.model.Expression
import worlds.gregs.hestia.core.task.api.TaskType
import worlds.gregs.hestia.core.task.model.await.Resendable

/**
 * Sends a chat box dialogue with the players head scaled [large] performing [animation] with the option to [continue] or not.
 * @param lines The lines for the player to say
 * @param animation The animation for the players head to perform @see [Expression]
 * @param large Whether to display the player head as a giant or not
 * @param continue Whether to show "Click here to continue" button or not
 */
data class PlayerChat(val lines: List<String>, val animation: Int, val large: Boolean = false, val `continue`: Boolean = true) : EntityAction(), TaskType<Unit>, Resendable {

    constructor(animation: Int = Expression.Talking, large: Boolean = false, `continue`: Boolean = true, text: String) : this(text.trimIndent().lines(), animation, large, `continue`)

    constructor(text: String) : this(text.trimIndent().lines(), Expression.Talking, false, true)

    init {
        check(lines.size <= 4) { "Maximum player dialogue lines 4" }
    }

    override lateinit var continuation: CancellableContinuation<Unit>
}