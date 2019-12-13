package worlds.gregs.hestia.core.display.update.logic.sync.mob.factories.local

import worlds.gregs.hestia.artemis.bag.EntitySyncBag
import worlds.gregs.hestia.core.display.update.api.SyncFactory
import worlds.gregs.hestia.core.display.update.logic.sync.SynchronizeSystem
import worlds.gregs.hestia.core.display.update.logic.sync.mob.stages.RemoveMobSync

class RemoveMobSyncFactory : SyncFactory<RemoveMobSync>(true, true, true) {

    override fun change(sync: SynchronizeSystem<*>, bag: EntitySyncBag, other: Int): Boolean {
        return bag.needsRemoval(other)
    }

    override fun create(main: Int, other: Int, update: Boolean): RemoveMobSync {
        return RemoveMobSync
    }

}