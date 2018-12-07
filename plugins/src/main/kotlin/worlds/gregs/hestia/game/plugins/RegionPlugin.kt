package worlds.gregs.hestia.game.plugins

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.game.plugin.Plugin.Companion.POST_UPDATE_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.VIEWPORT_PRIORITY
import worlds.gregs.hestia.game.plugins.core.systems.ViewportSystem
import worlds.gregs.hestia.game.plugins.movement.systems.calc.RouteFinderSystem
import worlds.gregs.hestia.game.plugins.region.systems.RegionBuilder
import worlds.gregs.hestia.game.plugins.region.systems.RegionCleanSystem
import worlds.gregs.hestia.game.plugins.region.systems.RegionEntitySystem
import worlds.gregs.hestia.game.plugins.region.systems.change.ClippingMaskSystem
import worlds.gregs.hestia.game.plugins.region.systems.change.RegionObjectSystem
import worlds.gregs.hestia.game.plugins.region.systems.change.TileCheckSystem
import worlds.gregs.hestia.game.plugins.region.systems.load.RegionCreation
import worlds.gregs.hestia.game.plugins.region.systems.RegionSystem
import worlds.gregs.hestia.game.plugins.region.systems.load.ChunkRotationSystem
import worlds.gregs.hestia.game.plugins.region.systems.load.MapClippingSystem
import worlds.gregs.hestia.game.plugins.region.systems.load.MapFileSystem
import worlds.gregs.hestia.game.plugins.region.systems.load.MapObjectSystem

class RegionPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(ChunkRotationSystem(), MapClippingSystem(), MapFileSystem(), MapObjectSystem(), RegionBuilder())
        b.with(RegionCreation(), TileCheckSystem(), RegionObjectSystem(), RegionEntitySystem())
        b.with(VIEWPORT_PRIORITY, ViewportSystem())
        b.with(RegionSystem(), ClippingMaskSystem(), RouteFinderSystem())
        b.with(POST_UPDATE_PRIORITY, RegionCleanSystem())
    }

}