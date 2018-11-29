package worlds.gregs.hestia.game.plugins

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.game.plugin.Plugin.Companion.POST_UPDATE_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.VIEWPORT_PRIORITY
import worlds.gregs.hestia.game.plugins.movement.systems.calc.RouteFinderSystem
import worlds.gregs.hestia.game.plugins.region.systems.*

class RegionPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(RegionCreation(), TileCheckSystem(), RegionObjectSystem(), RegionEntitySystem())
        b.with(VIEWPORT_PRIORITY, ViewportSystem())
        b.with(RegionSystem(), RegionMapSystem(), RouteFinderSystem())
        b.with(POST_UPDATE_PRIORITY, RegionCleanSystem())
    }

}