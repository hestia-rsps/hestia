package worlds.gregs.hestia.core.display.dialogue.model.type

import kotlinx.coroutines.CancellableContinuation
import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.task.api.Resendable
import worlds.gregs.hestia.core.task.api.TaskSuspension

/**
 * Sends a statement with a paragraph of text
 * @param lines The lines to send
 * @param `continue` whether to display the continue button
 */
data class Statement(val lines: List<String>, val `continue`: Boolean = true) : EntityAction(), TaskSuspension<Unit>, Resendable, InstantEvent {

    constructor(text: String, `continue`: Boolean = true) : this(text.trimIndent().lines(), `continue`)

    init {
        check(lines.size <= 5) { "Maximum statement lines 5" }
    }

    override lateinit var continuation: CancellableContinuation<Unit>
}