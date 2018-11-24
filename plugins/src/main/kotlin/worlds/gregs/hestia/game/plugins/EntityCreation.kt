package worlds.gregs.hestia.game.plugins

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.game.systems.creation.BotCreation
import worlds.gregs.hestia.game.mob.systems.MobCreation
import worlds.gregs.hestia.game.player.systems.PlayerCreation
import worlds.gregs.hestia.game.systems.creation.RegionCreation

class EntityCreation : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(PlayerCreation(), BotCreation(), MobCreation(), RegionCreation())
    }

}