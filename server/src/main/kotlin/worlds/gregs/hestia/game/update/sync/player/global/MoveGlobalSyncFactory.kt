package worlds.gregs.hestia.game.update.sync.player.global

import worlds.gregs.hestia.game.entity.Position
import worlds.gregs.hestia.network.update.sync.player.MoveGlobalPlayerSync

class MoveGlobalSyncFactory : BaseMovementSyncFactory<MoveGlobalPlayerSync>() {

    override fun create(position: Position, lastPosition: Position): MoveGlobalPlayerSync {
        return MoveGlobalPlayerSync(position, lastPosition)
    }

}