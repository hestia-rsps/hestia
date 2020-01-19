package worlds.gregs.hestia.core.display.dialogue

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.core.display.dialogue.logic.systems.*
import worlds.gregs.hestia.game.plugin.Plugin

class DialoguePlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(DialogueSystem())
        b.with(ConfirmDestroySystem(), DoubleChatSystem(), IntEntrySystem(), ItemBoxSystem(), NpcChatSystem(), OptionsSystem(), PlayerChatSystem(), StatementSystem(), StringEntrySystem())
    }

}