package worlds.gregs.hestia.core.display.update.logic.sync.player.factories.global

import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.display.update.logic.sync.player.stages.MoveGlobalPlayerSync

class MoveGlobalSyncFactory : BaseMovementSyncFactory<MoveGlobalPlayerSync>() {

    override fun create(position: Position, lastPosition: Position): MoveGlobalPlayerSync {
        return MoveGlobalPlayerSync.create(position, lastPosition)
    }

}