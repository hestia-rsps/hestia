package worlds.gregs.hestia.core.world.region

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.core.world.map.logic.systems.MapCollisionFlagSystem
import worlds.gregs.hestia.core.world.region.logic.systems.*
import worlds.gregs.hestia.core.world.region.logic.systems.load.ChunkRotationSystem
import worlds.gregs.hestia.core.world.region.logic.systems.load.RegionCreation
import worlds.gregs.hestia.core.world.region.logic.systems.load.RegionFileSystem
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.game.plugin.Plugin.Companion.POST_UPDATE_PRIORITY

class RegionPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(RegionsSystem(), DynamicSystem())
        b.with(ChunkRotationSystem(), RegionFileSystem(), RegionBuilderSystem())
        b.with(RegionCreation(), RegionPrioritySystem())
        b.with(RegionSystem(), MapCollisionFlagSystem())
        b.with(POST_UPDATE_PRIORITY, RegionCleanSystem())
    }

}