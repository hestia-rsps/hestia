package worlds.gregs.hestia.game.client.update.sync.player.local

import com.artemis.ComponentMapper
import worlds.gregs.hestia.api.client.update.components.Moving
import worlds.gregs.hestia.api.client.update.sync.SyncFactory
import worlds.gregs.hestia.api.movement.Mobile
import worlds.gregs.hestia.artemis.bag.EntitySyncBag
import worlds.gregs.hestia.game.client.update.sync.SynchronizeSystem
import worlds.gregs.hestia.game.client.update.sync.ViewportSystem.Companion.DEFAULT_VIEW_DISTANCE
import worlds.gregs.hestia.game.entity.components.Position
import worlds.gregs.hestia.network.update.sync.player.MoveLocalPlayerSync

class MoveLocalSyncFactory : SyncFactory<MoveLocalPlayerSync>(true, false, true) {

    private lateinit var movingMapper: ComponentMapper<Moving>
    private lateinit var mobileMapper: ComponentMapper<Mobile>
    private lateinit var positionMapper: ComponentMapper<Position>

    override fun change(sync: SynchronizeSystem<*>, bag: EntitySyncBag, other: Int): Boolean {
        return movingMapper.has(other)
    }

    override fun create(main: Int, other: Int, update: Boolean): MoveLocalPlayerSync {
        val position = positionMapper.get(other)
        val mobile = mobileMapper.get(other)
        val global = !position.withinDistance(mobile, DEFAULT_VIEW_DISTANCE)
        return MoveLocalPlayerSync.create(position, mobile, global, update)
    }

}