package worlds.gregs.hestia.core.world.land

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.core.world.land.logic.systems.LandObjectSystem
import worlds.gregs.hestia.core.world.land.logic.systems.LandSystem

class LandPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(LandObjectSystem(), LandSystem())
    }

}