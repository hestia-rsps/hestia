package worlds.gregs.hestia.game.plugins.dialogue.systems.types

import com.artemis.Entity
import worlds.gregs.hestia.api.dialogue.DialogueBase
import worlds.gregs.hestia.game.dialogue.Dialogue
import worlds.gregs.hestia.game.dialogue.Dialogues
import worlds.gregs.hestia.game.dialogue.faces.DialogueRedirect

class RedirectDialogue(override val directId: Int) : Dialogue(), DialogueRedirect {
    override fun send(entity: Entity, ui: DialogueBase) {}
}

fun Dialogues.redirect(id: Int) {
    //Connect redirect to the previous dialogue
    //Don't want it added to the queue as anything following a redirect will be ignored
    dialogue.connect(RedirectDialogue(id))
}