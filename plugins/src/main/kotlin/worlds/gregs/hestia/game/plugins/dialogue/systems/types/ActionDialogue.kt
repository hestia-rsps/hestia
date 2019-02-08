package worlds.gregs.hestia.game.plugins.dialogue.systems.types

import com.artemis.Entity
import worlds.gregs.hestia.api.dialogue.DialogueBase
import worlds.gregs.hestia.game.dialogue.Dialogue
import worlds.gregs.hestia.game.dialogue.Dialogues
import worlds.gregs.hestia.game.dialogue.faces.DialogueAction
import worlds.gregs.hestia.game.dialogue.faces.DialogueLinear
import worlds.gregs.hestia.game.dialogue.faces.DialogueRedirect

data class ActionDialogue(override val action: Entity.() -> Unit) : Dialogue(), DialogueAction, DialogueLinear, DialogueRedirect {
    override var next: Dialogue? = null
    override val directId: Int
        get() = next?.id ?: -1

    override fun send(entity: Entity, ui: DialogueBase) {}
}

fun Dialogues.action(action: Entity.() -> Unit) {
    dialogue.create(ActionDialogue(action))
}