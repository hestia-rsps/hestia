package worlds.gregs.hestia.game.plugins.dialogue.systems.types

import com.artemis.Entity
import worlds.gregs.hestia.api.dialogue.DialogueBase
import worlds.gregs.hestia.game.dialogue.Dialogue
import worlds.gregs.hestia.game.dialogue.Dialogues
import worlds.gregs.hestia.game.dialogue.faces.DialogueLinear

class BaseDialogue(action: Dialogues.() -> Unit) : Dialogue(), DialogueLinear {
    override fun send(entity: Entity, ui: DialogueBase) {}

    override var next: Dialogue? = null

    init {
        val list = ArrayList<Dialogue>()
        action(Dialogues(this, list))
        next = list.first()
    }
}