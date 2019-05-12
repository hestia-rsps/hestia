package worlds.gregs.hestia.core.plugins.dialogue.systems.types

import worlds.gregs.hestia.core.plugins.dialogue.components.DialogueContext
import worlds.gregs.hestia.core.plugins.dialogue.components.EntityDialogue
import worlds.gregs.hestia.game.queue.QueueScope
import worlds.gregs.hestia.services.wrap

data class ItemDialogue(override val lines: List<String>, override val title: String?, val item: Int) : EntityDialogue() {
    init {
        check(lines.size <= 4) { "Maximum player dialogue lines 4" }
    }
}

suspend fun QueueScope<DialogueContext>.item(text: String, item: Int = -1, title: String? = null) {
    ctx.dialogue = ItemDialogue(text.wrap(), title, item)
    suspend()
}
