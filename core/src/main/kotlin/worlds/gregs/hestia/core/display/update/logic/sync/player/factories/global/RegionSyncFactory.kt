package worlds.gregs.hestia.core.display.update.logic.sync.player.factories.global

import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.display.update.logic.sync.player.stages.RegionPlayerSync

class RegionSyncFactory : BaseMovementSyncFactory<RegionPlayerSync>() {

    override fun create(position: Position, lastPosition: Position): RegionPlayerSync {
        return RegionPlayerSync.create(position, lastPosition)
    }

}