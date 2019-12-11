package worlds.gregs.hestia.core.plugins

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.core.plugins.dialogue.systems.DialogueSystem
import worlds.gregs.hestia.core.plugins.dialogue.systems.EntityDialogueSystem
import worlds.gregs.hestia.core.plugins.dialogue.systems.types.IntegerEntryDialogueSystem
import worlds.gregs.hestia.core.plugins.dialogue.systems.types.OptionsDialogueSystem
import worlds.gregs.hestia.core.plugins.dialogue.systems.types.StatementDialogueSystem
import worlds.gregs.hestia.core.plugins.dialogue.systems.types.StringEntryDialogueSystem
import worlds.gregs.hestia.game.plugin.Plugin

class DialoguePlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(DialogueSystem(), EntityDialogueSystem(), StringEntryDialogueSystem(), StatementDialogueSystem(), OptionsDialogueSystem(), IntegerEntryDialogueSystem())
    }

}