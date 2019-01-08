package worlds.gregs.hestia.game.dialogue.faces

import com.artemis.Entity

interface DialogueAction {
    val action: Entity.() -> Unit
}