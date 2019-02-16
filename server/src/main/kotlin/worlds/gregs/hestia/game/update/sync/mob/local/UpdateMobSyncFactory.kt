package worlds.gregs.hestia.game.update.sync.mob.local

import worlds.gregs.hestia.game.update.Synchronize
import worlds.gregs.hestia.game.update.sync.SyncFactory
import worlds.gregs.hestia.network.update.sync.mob.UpdateMobSync

class UpdateMobSyncFactory : SyncFactory<UpdateMobSync>(true, true) {
    override fun change(sync: Synchronize, main: Int, other: Int): Boolean {
        return sync.hasUpdateBlocks(other)
    }

    override fun create(main: Int, other: Int, update: Boolean): UpdateMobSync {
       return UpdateMobSync()
    }

}