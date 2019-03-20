package worlds.gregs.hestia.game.update.sync.player.global

import worlds.gregs.hestia.game.entity.Position
import worlds.gregs.hestia.network.update.sync.player.HeightPlayerSync

class HeightSyncFactory : BaseMovementSyncFactory<HeightPlayerSync>() {

    override fun create(position: Position, lastPosition: Position): HeightPlayerSync {
        return HeightPlayerSync(position, lastPosition)
    }

}