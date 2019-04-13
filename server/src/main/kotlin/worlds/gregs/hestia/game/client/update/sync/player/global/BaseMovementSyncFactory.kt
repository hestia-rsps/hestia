package worlds.gregs.hestia.game.client.update.sync.player.global

import com.artemis.ComponentMapper
import worlds.gregs.hestia.api.client.components.Viewport
import worlds.gregs.hestia.api.client.update.sync.SyncFactory
import worlds.gregs.hestia.api.client.update.sync.SyncStage
import worlds.gregs.hestia.artemis.bag.EntitySyncBag
import worlds.gregs.hestia.game.client.update.sync.SynchronizeSystem
import worlds.gregs.hestia.game.entity.components.Position

abstract class BaseMovementSyncFactory<T : SyncStage> : SyncFactory<T>(false, false, false) {

    private lateinit var viewportMapper: ComponentMapper<Viewport>
    private lateinit var positionMapper: ComponentMapper<Position>

    override fun change(sync: SynchronizeSystem<*>, bag: EntitySyncBag, other: Int): Boolean {
        return false
    }

    override fun create(main: Int, other: Int, update: Boolean): T {
        val viewport = viewportMapper.get(main)
        val position = positionMapper.get(other)
        val lastPosition = viewport.getPosition(other)
        viewport.updatePosition(other, position)//Update
        return create(position, lastPosition)
    }

    abstract fun create(position: Position, lastPosition: Position) : T

}