package world.gregs.hestia.game.plugins

import com.artemis.WorldConfigurationBuilder
import world.gregs.hestia.game.plugin.Plugin
import world.gregs.hestia.game.systems.creation.BotCreation
import world.gregs.hestia.game.systems.creation.MobCreation
import world.gregs.hestia.game.systems.creation.PlayerCreation
import world.gregs.hestia.game.systems.creation.RegionCreation

class EntityCreation : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(PlayerCreation(), BotCreation(), MobCreation(), RegionCreation())
    }

}