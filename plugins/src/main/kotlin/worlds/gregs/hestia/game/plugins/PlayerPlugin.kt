package worlds.gregs.hestia.game.plugins

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.game.plugin.Plugin.Companion.POST_UPDATE_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.PRE_SHIFT_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.UPDATE_FINISH_PRIORITY
import worlds.gregs.hestia.game.plugins.player.systems.PlayerCreation
import worlds.gregs.hestia.game.plugins.player.systems.PlayerLoginSystem
import worlds.gregs.hestia.game.plugins.player.systems.chunk.PlayerChunkChangeSystem
import worlds.gregs.hestia.game.plugins.player.systems.chunk.PlayerChunkSubscriptionSystem
import worlds.gregs.hestia.game.plugins.player.systems.chunk.PlayerChunkSystem
import worlds.gregs.hestia.game.plugins.player.systems.chunk.map.PlayerChunkMap
import worlds.gregs.hestia.game.plugins.player.systems.chunk.map.PlayerChunkMapSystem
import worlds.gregs.hestia.game.plugins.player.systems.region.PlayerRegionChangeSystem
import worlds.gregs.hestia.game.plugins.player.systems.region.PlayerRegionSubscriptionSystem
import worlds.gregs.hestia.game.plugins.player.systems.region.PlayerRegionSystem
import worlds.gregs.hestia.game.plugins.player.systems.sync.PostPlayerSyncSystem

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