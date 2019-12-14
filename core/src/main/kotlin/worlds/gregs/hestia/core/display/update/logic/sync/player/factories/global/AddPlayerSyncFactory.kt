package worlds.gregs.hestia.core.display.update.logic.sync.player.factories.global

import com.artemis.ComponentMapper
import worlds.gregs.hestia.artemis.bag.map.EntitySyncBag
import worlds.gregs.hestia.core.display.client.model.components.Viewport
import worlds.gregs.hestia.core.display.update.api.SyncFactory
import worlds.gregs.hestia.core.display.update.logic.sync.SynchronizeSystem
import worlds.gregs.hestia.core.display.update.logic.sync.player.stages.AddPlayerSync
import worlds.gregs.hestia.core.entity.entity.model.components.Position

class AddPlayerSyncFactory : SyncFactory<AddPlayerSync>(false, false, true) {

    private lateinit var viewportMapper: ComponentMapper<Viewport>
    private lateinit var positionMapper: ComponentMapper<Position>

    override fun change(sync: SynchronizeSystem<*>, bag: EntitySyncBag, other: Int): Boolean {
        return bag.needsInsert(other) && bag.canInsert(other)
    }

    override fun create(main: Int, other: Int, update: Boolean): AddPlayerSync {
        val position = positionMapper.get(other)
        val lastPosition = viewportMapper.get(main).getPosition(other)
        return AddPlayerSync.create(position, createMovement(position, lastPosition))
    }
}