package worlds.gregs.hestia.core.display.update.model.components.direction

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.components.Size

data class Face(val x: Int, val y: Int, val sizeX: Int = 1, val sizeY: Int = 1) : EntityAction(), InstantEvent {
    constructor(position: Position, size: Size? = null) : this(position.x, position.y, size?.sizeX ?: 1, size?.sizeY ?: 1)
}