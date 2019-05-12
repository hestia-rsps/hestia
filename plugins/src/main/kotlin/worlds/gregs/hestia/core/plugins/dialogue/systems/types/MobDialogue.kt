package worlds.gregs.hestia.core.plugins.dialogue.systems.types

import worlds.gregs.hestia.core.plugins.dialogue.components.DialogueContext
import worlds.gregs.hestia.core.plugins.dialogue.components.EntityDialogue
import worlds.gregs.hestia.game.queue.QueueScope
import worlds.gregs.hestia.services.wrap

data class MobDialogue(override val lines: List<String>, override val title: String?, val mob: Int, val animation: Int) : EntityDialogue() {
    init {
        check(lines.size <= 4) { "Maximum mob dialogue lines 4" }
    }
}

suspend fun QueueScope<DialogueContext>.mob(text: String, id: Int = -1, anim: Int = -1, title: String? = null) {
    ctx.dialogue = MobDialogue(text.wrap(), title, id, anim)
    suspend()
}
