package worlds.gregs.hestia.core.entity.mob

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.core.entity.mob.logic.systems.MobCreation
import worlds.gregs.hestia.core.entity.mob.logic.systems.chunk.MobChunkChangeSystem
import worlds.gregs.hestia.core.entity.mob.logic.systems.chunk.MobChunkSubscriptionSystem
import worlds.gregs.hestia.core.entity.mob.logic.systems.chunk.MobChunkSystem
import worlds.gregs.hestia.core.entity.mob.logic.systems.chunk.map.MobChunkMap
import worlds.gregs.hestia.core.entity.mob.logic.systems.chunk.map.MobChunkMapSystem
import worlds.gregs.hestia.core.entity.mob.logic.systems.region.MobRegionChangeSystem
import worlds.gregs.hestia.core.entity.mob.logic.systems.region.MobRegionSubscriptionSystem
import worlds.gregs.hestia.core.entity.mob.logic.systems.sync.MobIndexSystem
import worlds.gregs.hestia.core.entity.mob.logic.systems.sync.PostMobSyncSystem
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.game.plugin.Plugin.Companion.POST_UPDATE_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.PRE_SHIFT_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.UPDATE_FINISH_PRIORITY

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