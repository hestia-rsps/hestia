package worlds.gregs.hestia.core.display.dialogue.logic.systems.types

import worlds.gregs.hestia.core.display.dialogue.logic.systems.EntityDialogue
import worlds.gregs.hestia.game.task.TaskScope
import worlds.gregs.hestia.artemis.wrap

data class ItemDialogue(override val lines: List<String>, override val title: String?, val item: Int) : EntityDialogue() {
    init {
        check(lines.size <= 4) { "Maximum player dialogue lines 4" }
    }
}

suspend fun TaskScope.item(text: String, item: Int = -1, title: String? = null) {
    deferral = ItemDialogue(text.wrap(), title, item)
    defer()
}
