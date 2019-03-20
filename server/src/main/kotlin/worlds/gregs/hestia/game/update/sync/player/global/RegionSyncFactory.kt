package worlds.gregs.hestia.game.update.sync.player.global

import worlds.gregs.hestia.game.entity.Position
import worlds.gregs.hestia.network.update.sync.player.RegionPlayerSync

class RegionSyncFactory : BaseMovementSyncFactory<RegionPlayerSync>() {

    override fun create(position: Position, lastPosition: Position): RegionPlayerSync {
        return RegionPlayerSync(position, lastPosition)
    }

}