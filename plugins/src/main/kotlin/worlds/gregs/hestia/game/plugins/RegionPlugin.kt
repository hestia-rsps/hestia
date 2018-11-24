package worlds.gregs.hestia.game.plugins

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.game.plugin.Plugin.Companion.MAP_REGION_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.PRE_SYNC_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.VIEWPORT_PRIORITY
import worlds.gregs.hestia.game.plugins.region.systems.MapRegionSystem
import worlds.gregs.hestia.game.plugins.region.systems.RegionCreation
import worlds.gregs.hestia.game.plugins.region.systems.RegionLoadSystem
import worlds.gregs.hestia.game.plugins.region.systems.ViewportSystem

class RegionPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(RegionCreation())
        b.with(MAP_REGION_PRIORITY, MapRegionSystem())
        b.with(VIEWPORT_PRIORITY, ViewportSystem())
        b.with(PRE_SYNC_PRIORITY, RegionLoadSystem())
    }

}