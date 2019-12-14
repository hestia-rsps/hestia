package worlds.gregs.hestia.core.display.update.logic.sync.player.factories.local

import com.artemis.ComponentMapper
import worlds.gregs.hestia.artemis.bag.EntitySyncBag
import worlds.gregs.hestia.core.display.client.model.components.Viewport
import worlds.gregs.hestia.core.display.update.api.SyncFactory
import worlds.gregs.hestia.game.update.SyncStage
import worlds.gregs.hestia.core.display.update.logic.sync.SynchronizeSystem
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.display.update.logic.sync.player.stages.RemovePlayerSync
import worlds.gregs.hestia.core.world.movement.api.Mobile

class RemovePlayerSyncFactory : SyncFactory<RemovePlayerSync>(true, false, true) {

    private lateinit var viewportMapper: ComponentMapper<Viewport>
    private lateinit var mobileMapper: ComponentMapper<Mobile>
    private lateinit var positionMapper: ComponentMapper<Position>

    override fun change(sync: SynchronizeSystem<*>, bag: EntitySyncBag, other: Int): Boolean {
        return bag.needsRemoval(other)
    }

    override fun create(main: Int, other: Int, update: Boolean): RemovePlayerSync {
        //Update viewport position
        val viewport = viewportMapper.get(main)
        if(mobileMapper.has(other)) {
            viewport.updatePosition(other, mobileMapper.get(other))
        }
        return RemovePlayerSync.create(createMovement(viewport, other))
    }

    private fun createMovement(viewport: Viewport, other: Int): SyncStage? {
        val position = positionMapper.get(other) ?: return null
        val lastPosition = viewport.getPosition(other)
        return createMovement(position, lastPosition)
    }
}