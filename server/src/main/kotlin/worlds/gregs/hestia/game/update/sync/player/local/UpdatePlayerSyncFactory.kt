package worlds.gregs.hestia.game.update.sync.player.local

import worlds.gregs.hestia.game.update.Synchronize
import worlds.gregs.hestia.game.update.sync.SyncFactory
import worlds.gregs.hestia.network.update.sync.player.UpdatePlayerSync

class UpdatePlayerSyncFactory : SyncFactory<UpdatePlayerSync>(true, false) {

    override fun change(sync: Synchronize, main: Int, other: Int): Boolean {
        return sync.hasUpdateBlocks(other)
    }

    override fun create(main: Int, other: Int, update: Boolean): UpdatePlayerSync {
        return UpdatePlayerSync()
    }

}