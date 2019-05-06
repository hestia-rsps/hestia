package worlds.gregs.hestia.game.client.update.sync.player.global

import worlds.gregs.hestia.game.entity.components.Position
import worlds.gregs.hestia.network.update.sync.player.HeightPlayerSync

class HeightSyncFactory : BaseMovementSyncFactory<HeightPlayerSync>() {

    override fun create(position: Position, lastPosition: Position): HeightPlayerSync {
        return HeightPlayerSync.create(position, lastPosition)
    }

}