package worlds.gregs.hestia.game.client.update.sync.player.global

import com.artemis.ComponentMapper
import worlds.gregs.hestia.api.client.components.Viewport
import worlds.gregs.hestia.api.client.update.sync.SyncFactory
import worlds.gregs.hestia.artemis.bag.EntitySyncBag
import worlds.gregs.hestia.game.client.update.sync.SynchronizeSystem
import worlds.gregs.hestia.game.entity.components.Position
import worlds.gregs.hestia.network.update.sync.player.AddPlayerSync

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