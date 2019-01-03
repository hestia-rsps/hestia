package worlds.gregs.hestia.game.plugins

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.game.plugin.Plugin.Companion.POST_UPDATE_PRIORITY
import worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem
import worlds.gregs.hestia.game.plugins.region.systems.*
import worlds.gregs.hestia.game.plugins.region.systems.load.ChunkRotationSystem
import worlds.gregs.hestia.game.plugins.region.systems.load.RegionCreation
import worlds.gregs.hestia.game.plugins.region.systems.load.RegionFileSystem

class RegionPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(RegionsSystem(), DynamicSystem())
        b.with(ChunkRotationSystem(), RegionFileSystem(), RegionBuilderSystem())
        b.with(RegionCreation(), RegionPrioritySystem())
        b.with(RegionSystem(), ClippingMaskSystem())
        b.with(POST_UPDATE_PRIORITY, RegionCleanSystem())
    }

}