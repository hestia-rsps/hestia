package worlds.gregs.hestia.game.plugins.dialogue.systems.types

import worlds.gregs.hestia.game.dialogue.Dialogue
import worlds.gregs.hestia.game.dialogue.faces.DialogueLinear
import worlds.gregs.hestia.game.dialogue.faces.DialogueTitle
import worlds.gregs.hestia.services.wrap

abstract class BaseChatDialogue(val text: String) : Dialogue(), DialogueLinear, DialogueTitle {
    override var next: Dialogue? = null
    override var title: String? = null
    val lines = text.wrap()

    override fun toString(): String {
        return "${super.toString()} $text"
    }

    companion object {
        internal const val ENTITY_ID = 241
        internal const val STATEMENT_ID = 210
        internal const val TWO_OPTIONS_ID = 236
        internal const val THREE_OPTIONS_ID = 231
        internal const val FOUR_OPTIONS_ID = 237
        internal const val FIVE_OPTIONS_ID = 238
    }
}

infix fun <T: BaseChatDialogue> T.title(title: String): T {
    this.title = title
    return this
}