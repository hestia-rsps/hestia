package worlds.gregs.hestia.core.display.update.logic.sync.npc.factories.local

import worlds.gregs.hestia.artemis.bag.map.EntitySyncBag
import worlds.gregs.hestia.core.display.update.api.SyncFactory
import worlds.gregs.hestia.core.display.update.logic.sync.SynchronizeSystem
import worlds.gregs.hestia.core.display.update.logic.sync.npc.stages.RemoveNpcSync

class RemoveNpcSyncFactory : SyncFactory<RemoveNpcSync>(true, true, true) {

    override fun change(sync: SynchronizeSystem<*>, bag: EntitySyncBag, other: Int): Boolean {
        return bag.needsRemoval(other)
    }

    override fun create(main: Int, other: Int, update: Boolean): RemoveNpcSync {
        return RemoveNpcSync
    }

}