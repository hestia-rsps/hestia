package worlds.gregs.hestia.game.client.update.sync.player.local

import worlds.gregs.hestia.api.client.update.sync.SyncFactory
import worlds.gregs.hestia.artemis.bag.EntitySyncBag
import worlds.gregs.hestia.game.client.update.sync.SynchronizeSystem
import worlds.gregs.hestia.network.update.sync.player.UpdatePlayerSync

class UpdatePlayerSyncFactory : SyncFactory<UpdatePlayerSync>(true, false, true) {

    override fun change(sync: SynchronizeSystem<*>, bag: EntitySyncBag, other: Int): Boolean {
        return sync.hasUpdateBlocks(other)
    }

    override fun create(main: Int, other: Int, update: Boolean): UpdatePlayerSync {
        return UpdatePlayerSync
    }

}