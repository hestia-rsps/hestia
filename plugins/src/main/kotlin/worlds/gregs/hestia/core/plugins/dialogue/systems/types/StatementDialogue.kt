package worlds.gregs.hestia.core.plugins.dialogue.systems.types

import worlds.gregs.hestia.core.plugins.dialogue.components.DialogueContext
import worlds.gregs.hestia.core.plugins.dialogue.components.LinesDialogue
import worlds.gregs.hestia.game.queue.QueueScope
import worlds.gregs.hestia.services.wrap

data class StatementDialogue(override val lines: List<String>, override val title: String?) : LinesDialogue() {
    init {
        check(lines.size < 5) { "Maximum statement dialogue lines 5" }
    }
}

suspend fun QueueScope<DialogueContext>.statement(text: String, title: String? = null) {
    ctx.dialogue = StatementDialogue(text.wrap(), title)
    suspend()
}