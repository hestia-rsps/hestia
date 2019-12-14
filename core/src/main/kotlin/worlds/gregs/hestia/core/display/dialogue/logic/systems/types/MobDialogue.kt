package worlds.gregs.hestia.core.display.dialogue.logic.systems.types

import worlds.gregs.hestia.core.display.dialogue.logic.systems.EntityDialogue
import worlds.gregs.hestia.game.task.TaskScope
import worlds.gregs.hestia.artemis.wrap

data class MobDialogue(override val lines: List<String>, override val title: String?, val mob: Int, val animation: Int) : EntityDialogue() {
    init {
        check(lines.size <= 4) { "Maximum mob dialogue lines 4" }
    }
}

suspend fun TaskScope.mob(text: String, id: Int = -1, anim: Int = -1, title: String? = null) {
    deferral = MobDialogue(text.wrap(), title, id, anim)
    defer()
}
