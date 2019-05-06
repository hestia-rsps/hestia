package worlds.gregs.hestia.game.client.update.sync.player.local

import com.artemis.ComponentMapper
import worlds.gregs.hestia.api.client.update.components.RunStep
import worlds.gregs.hestia.api.client.update.components.WalkStep
import worlds.gregs.hestia.api.client.update.sync.SyncFactory
import worlds.gregs.hestia.artemis.bag.EntitySyncBag
import worlds.gregs.hestia.game.client.update.sync.SynchronizeSystem
import worlds.gregs.hestia.network.update.sync.player.MovementPlayerSync

class PlayerMovementSyncFactory : SyncFactory<MovementPlayerSync>(true, false, true) {

    private lateinit var runMapper: ComponentMapper<RunStep>
    private lateinit var walkMapper: ComponentMapper<WalkStep>

    override fun change(sync: SynchronizeSystem<*>, bag: EntitySyncBag, other: Int): Boolean {
        return walkMapper.has(other)
    }

    override fun create(main: Int, other: Int, update: Boolean): MovementPlayerSync {
        return MovementPlayerSync.create(walkMapper.get(other).direction, runMapper.get(other)?.direction, update)
    }

}