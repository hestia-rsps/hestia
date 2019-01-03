package worlds.gregs.hestia.game.plugins

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.game.plugins.land.systems.LandObjectSystem
import worlds.gregs.hestia.game.plugins.land.systems.LandSystem

class LandPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(LandObjectSystem(), LandSystem())
    }

}