package worlds.gregs.hestia.core.display.dialogue.model.type

import kotlinx.coroutines.CancellableContinuation
import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.task.api.TaskType
import worlds.gregs.hestia.core.task.model.await.Resendable

data class Statement(val lines: List<String>, val `continue`: Boolean = true) : EntityAction(), TaskType<Unit>, Resendable {

    constructor(text: String, `continue`: Boolean = true) : this(text.trimIndent().lines(), `continue`)

    init {
        check(lines.size <= 5) { "Maximum statement lines 5" }
    }

    override lateinit var continuation: CancellableContinuation<Unit>
}