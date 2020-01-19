package worlds.gregs.hestia.core.display.update.logic.sync.npc.factories.local

import worlds.gregs.hestia.artemis.bag.map.EntitySyncBag
import worlds.gregs.hestia.core.display.update.api.SyncFactory
import worlds.gregs.hestia.core.display.update.logic.sync.SynchronizeSystem
import worlds.gregs.hestia.core.display.update.logic.sync.npc.stages.UpdateNpcSync

class UpdateNpcSyncFactory : SyncFactory<UpdateNpcSync>(true, true, true) {

    override fun change(sync: SynchronizeSystem<*>, bag: EntitySyncBag, other: Int): Boolean {
        return sync.hasUpdateBlocks(other)
    }

    override fun create(main: Int, other: Int, update: Boolean): UpdateNpcSync {
        return UpdateNpcSync
    }

}