package worlds.gregs.hestia.core.display.update.logic.sync

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.core.display.update.logic.sync.mob.factories.global.AddMobSyncFactory
import worlds.gregs.hestia.core.display.update.logic.sync.mob.factories.local.MobMovementSyncFactory
import worlds.gregs.hestia.core.display.update.logic.sync.mob.factories.local.RemoveMobSyncFactory
import worlds.gregs.hestia.core.display.update.logic.sync.mob.factories.local.UpdateMobSyncFactory
import worlds.gregs.hestia.core.display.update.logic.sync.player.factories.global.AddPlayerSyncFactory
import worlds.gregs.hestia.core.display.update.logic.sync.player.factories.global.HeightSyncFactory
import worlds.gregs.hestia.core.display.update.logic.sync.player.factories.global.MoveGlobalSyncFactory
import worlds.gregs.hestia.core.display.update.logic.sync.player.factories.global.RegionSyncFactory
import worlds.gregs.hestia.core.display.update.logic.sync.player.factories.local.MoveLocalSyncFactory
import worlds.gregs.hestia.core.display.update.logic.sync.player.factories.local.PlayerMovementSyncFactory
import worlds.gregs.hestia.core.display.update.logic.sync.player.factories.local.RemovePlayerSyncFactory
import worlds.gregs.hestia.core.display.update.logic.sync.player.factories.local.UpdatePlayerSyncFactory
import worlds.gregs.hestia.game.plugin.Plugin

class SynchronizePlugin : Plugin {

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