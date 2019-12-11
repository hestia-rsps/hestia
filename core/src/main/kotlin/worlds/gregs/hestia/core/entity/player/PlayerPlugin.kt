package worlds.gregs.hestia.core.entity.player

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.core.entity.bot.logic.BotFactory
import worlds.gregs.hestia.core.entity.mob.logic.MobFactory
import worlds.gregs.hestia.core.entity.player.logic.PlayerFactory
import worlds.gregs.hestia.core.entity.player.logic.systems.PlayerCreation
import worlds.gregs.hestia.core.entity.player.logic.systems.PlayerLoginSystem
import worlds.gregs.hestia.core.entity.player.logic.systems.chunk.PlayerChunkChangeSystem
import worlds.gregs.hestia.core.entity.player.logic.systems.chunk.PlayerChunkSubscriptionSystem
import worlds.gregs.hestia.core.entity.player.logic.systems.chunk.PlayerChunkSystem
import worlds.gregs.hestia.core.entity.player.logic.systems.chunk.map.PlayerChunkMap
import worlds.gregs.hestia.core.entity.player.logic.systems.chunk.map.PlayerChunkMapSystem
import worlds.gregs.hestia.core.entity.player.logic.systems.region.PlayerRegionChangeSystem
import worlds.gregs.hestia.core.entity.player.logic.systems.region.PlayerRegionSubscriptionSystem
import worlds.gregs.hestia.core.entity.player.logic.systems.region.PlayerRegionSystem
import worlds.gregs.hestia.core.entity.player.logic.systems.sync.PostPlayerSyncSystem
import worlds.gregs.hestia.game.archetypes.EntityFactory
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.game.plugin.Plugin.Companion.POST_UPDATE_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.PRE_SHIFT_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.UPDATE_FINISH_PRIORITY

class PlayerPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(PlayerCreation())
        b.with(PlayerChunkSubscriptionSystem(), PlayerChunkSystem(), PlayerRegionSubscriptionSystem(), PlayerRegionSystem(), PlayerLoginSystem())
        b.with(PlayerChunkMap())
        b.with(PRE_SHIFT_PRIORITY, PlayerChunkChangeSystem(), PlayerRegionChangeSystem())
        b.with(UPDATE_FINISH_PRIORITY, PostPlayerSyncSystem())
        b.with(POST_UPDATE_PRIORITY, PlayerChunkMapSystem())
    }

}