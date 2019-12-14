package worlds.gregs.hestia.core.display.update.logic.sync.player.factories.global

import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.display.update.logic.sync.player.stages.HeightPlayerSync

class HeightSyncFactory : BaseMovementSyncFactory<HeightPlayerSync>() {

    override fun create(position: Position, lastPosition: Position): HeightPlayerSync {
        return HeightPlayerSync.create(position, lastPosition)
    }

}