package worlds.gregs.hestia.core.display.update.logic.sync

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.core.display.update.logic.sync.npc.factories.global.AddNpcSyncFactory
import worlds.gregs.hestia.core.display.update.logic.sync.npc.factories.local.NpcMovementSyncFactory
import worlds.gregs.hestia.core.display.update.logic.sync.npc.factories.local.RemoveNpcSyncFactory
import worlds.gregs.hestia.core.display.update.logic.sync.npc.factories.local.UpdateNpcSyncFactory
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

        //Local npc view changes
        b.with(NpcMovementSyncFactory(), UpdateNpcSyncFactory(), RemoveNpcSyncFactory())
        //Global npc view changes
        b.with(AddNpcSyncFactory())
    }

}