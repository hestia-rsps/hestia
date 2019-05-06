package worlds.gregs.hestia.api.client.update.sync

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.game.client.update.sync.mob.global.AddMobSyncFactory
import worlds.gregs.hestia.game.client.update.sync.mob.local.MobMovementSyncFactory
import worlds.gregs.hestia.game.client.update.sync.mob.local.RemoveMobSyncFactory
import worlds.gregs.hestia.game.client.update.sync.mob.local.UpdateMobSyncFactory
import worlds.gregs.hestia.game.client.update.sync.player.global.AddPlayerSyncFactory
import worlds.gregs.hestia.game.client.update.sync.player.global.HeightSyncFactory
import worlds.gregs.hestia.game.client.update.sync.player.global.MoveGlobalSyncFactory
import worlds.gregs.hestia.game.client.update.sync.player.global.RegionSyncFactory
import worlds.gregs.hestia.game.client.update.sync.player.local.MoveLocalSyncFactory
import worlds.gregs.hestia.game.client.update.sync.player.local.PlayerMovementSyncFactory
import worlds.gregs.hestia.game.client.update.sync.player.local.RemovePlayerSyncFactory
import worlds.gregs.hestia.game.client.update.sync.player.local.UpdatePlayerSyncFactory
import worlds.gregs.hestia.game.plugin.Plugin

class SyncPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        //Local player view changes
        b.with(RemovePlayerSyncFactory(), MoveLocalSyncFactory(), PlayerMovementSyncFactory(), UpdatePlayerSyncFactory())
        //Global player view changes
        b.with(AddPlayerSyncFactory(), HeightSyncFactory(), RegionSyncFactory(), MoveGlobalSyncFactory())

        //Local mob view changes
        b.with(MobMovementSyncFactory(), UpdateMobSyncFactory(), RemoveMobSyncFactory())
        //Global mob view changes
        b.with(AddMobSyncFactory())
    }

}