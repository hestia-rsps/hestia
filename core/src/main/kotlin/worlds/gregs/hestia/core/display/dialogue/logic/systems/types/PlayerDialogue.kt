package worlds.gregs.hestia.core.display.dialogue.logic.systems.types

import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import worlds.gregs.hestia.core.task.api.Task

data class PlayerDialogue(override val lines: List<String>, override val title: String?, val animation: Int, override var continuation: CancellableContinuation<Unit>) : EntityDialogue() {
    init {
        check(lines.size <= 4) { "Maximum player dialogue lines 4" }
    }
}
suspend fun Task.player(text: String, anim: Int = -1, title: String? = null) = suspendCancellableCoroutine<Unit> {
    suspension = PlayerDialogue(text.trimIndent().lines(), title, anim, it)
}
