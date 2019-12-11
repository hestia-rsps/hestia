package worlds.gregs.hestia.core.plugins

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.core.plugins.bot.systems.BotCreation

class BotPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(BotCreation())
    }

}