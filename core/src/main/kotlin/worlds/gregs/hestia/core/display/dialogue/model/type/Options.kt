package worlds.gregs.hestia.core.display.dialogue.model.type

import kotlinx.coroutines.CancellableContinuation
import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.task.api.Resendable
import worlds.gregs.hestia.core.task.api.TaskSuspension

/**
 * Sends a multi-choice dialogue to the client and returns the option selected
 * @param options The options to send (between 2-5)
 * @param title The title (default null)
 */
data class Options(val options: List<String>, val title: String? = null) : EntityAction(), TaskSuspension<Int>, Resendable, InstantEvent {

    constructor(options: String, title: String? = null) : this(options.trimIndent().lines(), title)

    init {
        check(options.size <= 5) { "Maximum options 5" }
        check(options.size >= 2) { "Minimum options 2" }
    }

    override lateinit var continuation: CancellableContinuation<Int>
}