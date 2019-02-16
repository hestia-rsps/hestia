package worlds.gregs.hestia.game.update.sync.player.local

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.entity.Position
import worlds.gregs.hestia.api.movement.Mobile
import worlds.gregs.hestia.game.update.Synchronize
import worlds.gregs.hestia.game.update.components.Moving
import worlds.gregs.hestia.game.update.sync.SyncFactory
import worlds.gregs.hestia.network.update.sync.player.MoveLocalPlayerSync

class MoveLocalSyncFactory : SyncFactory<MoveLocalPlayerSync>(true, false) {

    private lateinit var movingMapper: ComponentMapper<Moving>
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var mobileMapper: ComponentMapper<Mobile>

    override fun change(sync: Synchronize, main: Int, other: Int): Boolean {
        return movingMapper.has(other)
    }

    override fun create(main: Int, other: Int, update: Boolean): MoveLocalPlayerSync {
        val position = positionMapper.get(other)
        val mobile = mobileMapper.get(other)
        val global = !position.withinDistance(mobile, 15)
        return MoveLocalPlayerSync(position, mobile, global, update)
    }

}