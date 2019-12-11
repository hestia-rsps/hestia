package worlds.gregs.hestia.game.client.update.sync.player.global

import worlds.gregs.hestia.game.entity.components.Position
import worlds.gregs.hestia.network.update.sync.player.MoveGlobalPlayerSync

class MoveGlobalSyncFactory : BaseMovementSyncFactory<MoveGlobalPlayerSync>() {

    override fun create(position: Position, lastPosition: Position): MoveGlobalPlayerSync {
        return MoveGlobalPlayerSync.create(position, lastPosition)
    }

}