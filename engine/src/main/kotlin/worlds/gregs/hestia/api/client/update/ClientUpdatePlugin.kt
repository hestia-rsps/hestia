package worlds.gregs.hestia.api.client.update

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.api.client.update.block.BlockPlugin
import worlds.gregs.hestia.api.client.update.sync.SyncPlugin
import worlds.gregs.hestia.game.client.update.PlayerIndexSystem
import worlds.gregs.hestia.game.client.update.sync.ViewportSystem
import worlds.gregs.hestia.game.client.update.sync.mob.MobSynchronize
import worlds.gregs.hestia.game.client.update.sync.player.PlayerSynchronize
import worlds.gregs.hestia.game.client.update.sync.player.global.AddPlayerSyncFactory
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.game.plugin.Plugin.Companion.PRE_SYNC_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.UPDATE_SYNC_PRIORITY

class ClientUpdatePlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(PRE_SYNC_PRIORITY, ViewportSystem())
        b.with(UPDATE_SYNC_PRIORITY, PlayerSynchronize(), MobSynchronize())
        b.with(Plugin.PLAYER_INDEX_PRIORITY, PlayerIndexSystem())

        b.dependsOn(SyncPlugin::class.java)
        b.dependsOn(BlockPlugin::class.java)
    }

}