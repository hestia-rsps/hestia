package worlds.gregs.hestia.game.plugins

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.game.plugins.dialogue.systems.DialoguesSystem

class DialoguePlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(DialoguesSystem())
    }

}