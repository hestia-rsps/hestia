package worlds.gregs.hestia.core.entity.bot

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.core.entity.bot.logic.BotFactory
import worlds.gregs.hestia.core.entity.bot.logic.systems.BotCreation
import worlds.gregs.hestia.game.archetypes.EntityFactory
import worlds.gregs.hestia.game.plugin.Plugin

class BotPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(BotCreation())
    }

}