package worlds.gregs.hestia.core.display.dialogue.logic.systems.types

import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import worlds.gregs.hestia.core.task.api.Task

data class MobDialogue(override val lines: List<String>, override val title: String?, val mob: Int, val animation: Int, override val continuation: CancellableContinuation<Unit>) : EntityDialogue() {
    init {
        check(lines.size <= 4) { "Maximum mob dialogue lines 4" }
    }
}

suspend fun Task.mob(text: String, id: Int = -1, anim: Int = -1, title: String? = null) = suspendCancellableCoroutine<Unit> {
    suspension = MobDialogue(text.trimIndent().lines(), title, id, anim, it)
}