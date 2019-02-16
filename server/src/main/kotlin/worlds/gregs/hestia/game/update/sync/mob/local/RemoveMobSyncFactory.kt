package worlds.gregs.hestia.game.update.sync.mob.local

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.update.Synchronize
import worlds.gregs.hestia.game.update.components.Moving
import worlds.gregs.hestia.game.update.sync.SyncFactory
import worlds.gregs.hestia.network.update.sync.mob.RemoveMobSync

class RemoveMobSyncFactory : SyncFactory<RemoveMobSync>(true, true) {

    private lateinit var movingMapper: ComponentMapper<Moving>

    override fun change(sync: Synchronize, main: Int, other: Int): Boolean {
        return !world.entityManager.isActive(other) || !withinDistance(main, other) || movingMapper.has(other)
    }

    override fun create(main: Int, other: Int, update: Boolean): RemoveMobSync {
        return RemoveMobSync()
    }

}