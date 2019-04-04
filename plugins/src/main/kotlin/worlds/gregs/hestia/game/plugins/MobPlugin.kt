package worlds.gregs.hestia.game.plugins

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.game.plugin.Plugin.Companion.POST_UPDATE_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.PRE_SHIFT_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.UPDATE_FINISH_PRIORITY
import worlds.gregs.hestia.game.plugins.mob.systems.MobCreation
import worlds.gregs.hestia.game.plugins.mob.systems.chunk.MobChunkChangeSystem
import worlds.gregs.hestia.game.plugins.mob.systems.chunk.MobChunkSubscriptionSystem
import worlds.gregs.hestia.game.plugins.mob.systems.chunk.MobChunkSystem
import worlds.gregs.hestia.game.plugins.mob.systems.chunk.map.MobChunkMap
import worlds.gregs.hestia.game.plugins.mob.systems.chunk.map.MobChunkMapSystem
import worlds.gregs.hestia.game.plugins.mob.systems.region.MobRegionChangeSystem
import worlds.gregs.hestia.game.plugins.mob.systems.region.MobRegionSubscriptionSystem
import worlds.gregs.hestia.game.plugins.mob.systems.sync.MobIndexSystem
import worlds.gregs.hestia.game.plugins.mob.systems.sync.PostMobSyncSystem

class MobPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(MobChunkMap(), MobCreation())
        b.with(MobChunkSubscriptionSystem(), MobRegionSubscriptionSystem(), MobChunkSystem())
        b.with(PRE_SHIFT_PRIORITY, MobChunkChangeSystem(), MobRegionChangeSystem())
        b.with(POST_UPDATE_PRIORITY, MobChunkMapSystem())
        b.with(UPDATE_FINISH_PRIORITY, PostMobSyncSystem())
        b.with(MobIndexSystem())
    }

}