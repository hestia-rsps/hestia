package worlds.gregs.hestia.core.display.update

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.core.display.update.logic.block.BlockPlugin
import worlds.gregs.hestia.core.display.update.logic.sync.SynchronizePlugin
import worlds.gregs.hestia.core.display.update.logic.sync.ViewportSystem
import worlds.gregs.hestia.core.display.update.logic.sync.npc.NpcSynchronize
import worlds.gregs.hestia.core.display.update.logic.sync.player.PlayerSynchronize
import worlds.gregs.hestia.core.display.update.logic.systems.PlayerIndexSystem
import worlds.gregs.hestia.core.display.update.logic.systems.PostEntitySyncSystem
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.game.plugin.Plugin.Companion.PRE_SYNC_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.UPDATE_SYNC_PRIORITY

class ClientUpdatePlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(PRE_SYNC_PRIORITY, ViewportSystem())
        b.with(UPDATE_SYNC_PRIORITY, PlayerSynchronize(), NpcSynchronize())
        b.with(Plugin.PLAYER_INDEX_PRIORITY, PlayerIndexSystem())
        b.with(Plugin.UPDATE_FINISH_PRIORITY, PostEntitySyncSystem())

        b.dependsOn(SynchronizePlugin::class.java)
        b.dependsOn(BlockPlugin::class.java)
    }

}