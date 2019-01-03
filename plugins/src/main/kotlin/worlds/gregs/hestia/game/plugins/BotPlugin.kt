package worlds.gregs.hestia.game.plugins

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.game.plugins.bot.systems.BotCreation

class BotPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(BotCreation())
    }

}