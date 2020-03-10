package worlds.gregs.hestia.core.entity.npc

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.core.entity.npc.logic.systems.NpcCreation
import worlds.gregs.hestia.core.entity.npc.logic.systems.chunk.NpcChunkChangeSystem
import worlds.gregs.hestia.core.entity.npc.logic.systems.chunk.NpcChunkSubscriptionSystem
import worlds.gregs.hestia.core.entity.npc.logic.systems.chunk.NpcChunkSystem
import worlds.gregs.hestia.core.entity.npc.logic.systems.chunk.map.NpcChunkMap
import worlds.gregs.hestia.core.entity.npc.logic.systems.chunk.map.NpcChunkMapSystem
import worlds.gregs.hestia.core.entity.npc.logic.systems.region.NpcRegionChangeSystem
import worlds.gregs.hestia.core.entity.npc.logic.systems.region.NpcRegionSubscriptionSystem
import worlds.gregs.hestia.core.entity.npc.logic.systems.sync.NpcIndexSystem
import worlds.gregs.hestia.core.entity.npc.logic.systems.sync.PostNpcSyncSystem
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.game.plugin.Plugin.Companion.POST_UPDATE_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.PRE_SHIFT_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.UPDATE_FINISH_PRIORITY

class NpcPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(NpcChunkMap(), NpcCreation())
        b.with(NpcChunkSubscriptionSystem(), NpcRegionSubscriptionSystem(), NpcChunkSystem())
        b.with(PRE_SHIFT_PRIORITY, NpcChunkChangeSystem(), NpcRegionChangeSystem())
        b.with(POST_UPDATE_PRIORITY, NpcChunkMapSystem())
        b.with(UPDATE_FINISH_PRIORITY, PostNpcSyncSystem())
        b.with(NpcIndexSystem())
    }

}