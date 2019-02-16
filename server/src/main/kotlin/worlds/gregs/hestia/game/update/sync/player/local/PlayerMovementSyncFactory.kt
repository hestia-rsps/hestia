package worlds.gregs.hestia.game.update.sync.player.local

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.update.Synchronize
import worlds.gregs.hestia.game.update.components.RunStep
import worlds.gregs.hestia.game.update.components.WalkStep
import worlds.gregs.hestia.game.update.sync.SyncFactory
import worlds.gregs.hestia.network.update.sync.player.MovementPlayerSync

class PlayerMovementSyncFactory : SyncFactory<MovementPlayerSync>(true, false) {

    private lateinit var runMapper: ComponentMapper<RunStep>
    private lateinit var walkMapper: ComponentMapper<WalkStep>

    override fun change(sync: Synchronize, main: Int, other: Int): Boolean {
        return walkMapper.has(other)
    }

    override fun create(main: Int, other: Int, update: Boolean): MovementPlayerSync {
        return MovementPlayerSync(walkMapper.get(other).direction, if (runMapper.has(other)) runMapper.get(other).direction else null, update)
    }

}