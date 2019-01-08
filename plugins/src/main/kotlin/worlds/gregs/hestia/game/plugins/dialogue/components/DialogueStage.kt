package worlds.gregs.hestia.game.plugins.dialogue.components

import com.artemis.Component

class DialogueStage : Component() {
    lateinit var name: String
    var stage: Int = -1
}