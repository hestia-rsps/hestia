package worlds.gregs.hestia.core.display.dialogue.model.type

import kotlinx.coroutines.CancellableContinuation
import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.display.dialogue.model.Expression
import worlds.gregs.hestia.core.task.api.TaskType
import worlds.gregs.hestia.core.task.model.await.Resendable

/**
 * Sends a chat box dialogue with the head of [mob] scaled [large] performing [animation] with the option to [continue] or not.
 * @param lines The lines for the mob to say
 * @param mob The type id of the mob who's head to display
 * @param animation The animation for the mob head to perform @see [Expression]
 * @param large Whether to display the mob head as a giant or not
 * @param continue Whether to show "Click here to continue" button or not
 */
data class MobChat(val lines: List<String>, val mob: Int, val animation: Int, val large: Boolean = false, val `continue`: Boolean = true) : EntityAction(), TaskType<Unit>, Resendable {

    constructor(mob: Int, animation: Int = Expression.Talking, large: Boolean = false, `continue`: Boolean = true, text: String) : this(text.trimIndent().lines(), mob, animation, large, `continue`)

    constructor(mob: Int, text: String) : this(text.trimIndent().lines(), mob, Expression.Talking, false, true)

    init {
        check(lines.size <= 4) { "Maximum mob dialogue lines 4" }
    }

    override lateinit var continuation: CancellableContinuation<Unit>
}