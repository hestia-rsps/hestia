package worlds.gregs.hestia.game.update.sync.player.global

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.entity.Position
import worlds.gregs.hestia.game.update.Synchronize
import worlds.gregs.hestia.game.update.sync.SyncFactory
import worlds.gregs.hestia.network.update.sync.player.AddPlayerSync

class AddPlayerSyncFactory : SyncFactory<AddPlayerSync>(false, false) {

    private lateinit var positionMapper: ComponentMapper<Position>

    override fun change(sync: Synchronize, main: Int, other: Int): Boolean {
        return withinDistance(main, other)
    }

    override fun create(main: Int, other: Int, update: Boolean): AddPlayerSync {
        return AddPlayerSync(positionMapper.get(other), if (positionMapper.has(other)) createMovement(main, other) else null)
    }

}