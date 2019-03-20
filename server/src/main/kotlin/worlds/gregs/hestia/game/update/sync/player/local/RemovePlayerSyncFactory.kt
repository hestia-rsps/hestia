package worlds.gregs.hestia.game.update.sync.player.local

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.client.Viewport
import worlds.gregs.hestia.api.movement.Mobile
import worlds.gregs.hestia.game.update.Synchronize
import worlds.gregs.hestia.game.update.sync.SyncFactory
import worlds.gregs.hestia.network.update.sync.player.RemovePlayerSync

class RemovePlayerSyncFactory : SyncFactory<RemovePlayerSync>(true, false) {

    private lateinit var viewportMapper: ComponentMapper<Viewport>
    private lateinit var mobileMapper: ComponentMapper<Mobile>

    override fun change(sync: Synchronize, main: Int, other: Int): Boolean {
        return !world.entityManager.isActive(other) || !withinDistance(main, other)
    }

    override fun create(main: Int, other: Int, update: Boolean): RemovePlayerSync {
        //Update viewport position
        val viewport = viewportMapper.get(main)
        if(mobileMapper.has(other)) {
            viewport.updatePosition(other, mobileMapper.get(other))
        }
        return RemovePlayerSync(createMovement(main, other))
    }

}