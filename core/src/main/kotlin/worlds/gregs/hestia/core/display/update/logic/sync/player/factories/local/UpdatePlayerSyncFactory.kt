package worlds.gregs.hestia.core.display.update.logic.sync.player.factories.local

import worlds.gregs.hestia.artemis.bag.map.EntitySyncBag
import worlds.gregs.hestia.core.display.update.api.SyncFactory
import worlds.gregs.hestia.core.display.update.logic.sync.SynchronizeSystem
import worlds.gregs.hestia.core.display.update.logic.sync.player.stages.UpdatePlayerSync

class UpdatePlayerSyncFactory : SyncFactory<UpdatePlayerSync>(true, false, true) {

    override fun change(sync: SynchronizeSystem<*>, bag: EntitySyncBag, other: Int): Boolean {
        return sync.hasUpdateBlocks(other)
    }

    override fun create(main: Int, other: Int, update: Boolean): UpdatePlayerSync {
        return UpdatePlayerSync
    }

}