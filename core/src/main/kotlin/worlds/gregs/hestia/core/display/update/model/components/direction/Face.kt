package worlds.gregs.hestia.core.display.update.model.components.direction

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.components.Size

data class Face(val x: Int, val y: Int) : EntityAction(), InstantEvent {
    constructor(position: Position, size: Size? = null) : this(FaceUpdate.getFaceX(position, size?.sizeX ?: 1), FaceUpdate.getFaceY(position, size?.sizeY ?: 1))
}