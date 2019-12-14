package worlds.gregs.hestia.core.display.dialogue

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.core.display.dialogue.logic.systems.DialogueSystem
import worlds.gregs.hestia.core.display.dialogue.logic.systems.EntityDialogueSystem
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.IntegerEntryDialogueSystem
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.OptionsDialogueSystem
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.StatementDialogueSystem
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.StringEntryDialogueSystem
import worlds.gregs.hestia.game.plugin.Plugin

class DialoguePlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(DialogueSystem(), EntityDialogueSystem(), StringEntryDialogueSystem(), StatementDialogueSystem(), OptionsDialogueSystem(), IntegerEntryDialogueSystem())
    }

}