package worlds.gregs.hestia.core.display.dialogue

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.core.display.dialogue.logic.systems.DialogueBoxSystem
import worlds.gregs.hestia.core.display.dialogue.logic.systems.DialogueSystem
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.*
import worlds.gregs.hestia.game.plugin.Plugin

class DialoguePlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(DialogueSystem(), DialogueBoxSystem(), EntityDialogueSystem(), StringEntryDialogueSystem(), StatementDialogueSystem(), OptionsDialogueSystem(), IntegerEntryDialogueSystem())
    }

}