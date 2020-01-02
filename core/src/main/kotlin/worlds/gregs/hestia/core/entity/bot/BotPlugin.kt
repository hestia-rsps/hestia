package worlds.gregs.hestia.core.entity.bot

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.core.entity.bot.logic.systems.BotCreation
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.game.plugin.Plugin.Companion.POST_UPDATE_PRIORITY

class BotPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(POST_UPDATE_PRIORITY, BotCreation(true))
    }

}