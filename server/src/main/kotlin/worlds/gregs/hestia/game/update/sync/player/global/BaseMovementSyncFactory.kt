package worlds.gregs.hestia.game.update.sync.player.global

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.entity.Position
import worlds.gregs.hestia.game.client.Viewport
import worlds.gregs.hestia.game.update.Synchronize
import worlds.gregs.hestia.game.update.sync.SyncFactory
import worlds.gregs.hestia.game.update.sync.SyncStage

abstract class BaseMovementSyncFactory<T : SyncStage> : SyncFactory<T>(false, false) {

    private lateinit var viewportMapper: ComponentMapper<Viewport>
    private lateinit var positionMapper: ComponentMapper<Position>

    override fun change(sync: Synchronize, main: Int, other: Int): Boolean {
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