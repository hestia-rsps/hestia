package worlds.gregs.hestia.core.display.dialogue.logic.systems.types

import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import worlds.gregs.hestia.core.task.api.Task

data class ItemDialogue(override val lines: List<String>, override val title: String?, val item: Int, override val continuation: CancellableContinuation<Unit>) : EntityDialogue() {
    init {
        check(lines.size <= 4) { "Maximum player dialogue lines 4" }
    }
}

suspend fun Task.item(text: String, item: Int = -1, title: String? = null) = suspendCancellableCoroutine<Unit> {
    suspension = ItemDialogue(text.trimIndent().lines(), title, item, it)
}