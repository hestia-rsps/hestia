package worlds.gregs.hestia.core.display.update.logic.sync.player.factories.local

import com.artemis.ComponentMapper
import worlds.gregs.hestia.artemis.bag.map.EntitySyncBag
import worlds.gregs.hestia.core.display.update.model.components.RunStep
import worlds.gregs.hestia.core.display.update.model.components.WalkStep
import worlds.gregs.hestia.core.display.update.api.SyncFactory
import worlds.gregs.hestia.core.display.update.logic.sync.SynchronizeSystem
import worlds.gregs.hestia.core.display.update.logic.sync.player.stages.MovementPlayerSync

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