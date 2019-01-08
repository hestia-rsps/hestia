package worlds.gregs.hestia.game.plugins.dialogue.systems.types

import worlds.gregs.hestia.game.dialogue.Dialogues

class StatementDialogue(text: String) : BaseEntityDialogue(text) {

    override fun getInterfaceId(): Int {
        return STATEMENT_ID + lines.size - 1
    }

    override fun getComponentStart(): Int {
        return 0
    }

    override fun getMaxLines(): Int {
        return 5
    }

}

fun Dialogues.statement(text: String): StatementDialogue {
    val base = StatementDialogue(text)
    list.add(dialogue.create(base))
    return base
}