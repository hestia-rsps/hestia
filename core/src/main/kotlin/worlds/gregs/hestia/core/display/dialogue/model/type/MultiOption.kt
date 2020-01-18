package worlds.gregs.hestia.core.display.dialogue.model.type

import kotlinx.coroutines.CancellableContinuation
import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.task.api.TaskType
import worlds.gregs.hestia.core.task.model.await.Resendable

data class MultiOption(val options: List<String>, val title: String? = null) : EntityAction(), TaskType<Int>, Resendable {

    constructor(options: String, title: String? = null) : this(options.trimIndent().lines(), title)

    init {
        check(options.size <= 5) { "Maximum options 5" }
        check(options.size >= 2) { "Minimum options 2" }
    }

    override lateinit var continuation: CancellableContinuation<Int>
}