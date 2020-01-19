package worlds.gregs.hestia.core.display.update.logic.sync.npc.factories.local

import com.artemis.ComponentMapper
import worlds.gregs.hestia.artemis.bag.map.EntitySyncBag
import worlds.gregs.hestia.core.display.update.api.SyncFactory
import worlds.gregs.hestia.core.display.update.logic.sync.SynchronizeSystem
import worlds.gregs.hestia.core.display.update.logic.sync.npc.stages.MovementNpcSync
import worlds.gregs.hestia.core.display.update.model.components.RunStep
import worlds.gregs.hestia.core.display.update.model.components.WalkStep

class NpcMovementSyncFactory : SyncFactory<MovementNpcSync>(true, true, true) {

    private lateinit var runMapper: ComponentMapper<RunStep>
    private lateinit var walkMapper: ComponentMapper<WalkStep>

    override fun change(sync: SynchronizeSystem<*>, bag: EntitySyncBag, other: Int): Boolean {
        return walkMapper.has(other)
    }

    override fun create(main: Int, other: Int, update: Boolean): MovementNpcSync {
        return MovementNpcSync.create(walkMapper.get(other).direction, if (runMapper.has(other)) runMapper.get(other).direction else null, update)
    }
}