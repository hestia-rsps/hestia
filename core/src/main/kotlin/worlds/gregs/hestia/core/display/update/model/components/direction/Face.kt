package worlds.gregs.hestia.core.display.update.model.components.direction

import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.components.Size

data class Face(val deltaX: Int, val deltaY: Int) : EntityAction() {

    constructor(position: Position, size: Size? = null) : this(getFaceX(position, size?.sizeX ?: 1), getFaceY(position, size?.sizeY ?: 1))

    companion object {
        fun getFaceX(position: Position, sizeX: Int, sizeY: Int = -1, rotation: Int = -1): Int {
            return position.x + ((if (rotation == 1 || rotation == 3) sizeY else sizeX) - 1) / 2
        }

        fun getFaceY(position: Position, sizeY: Int, sizeX: Int = -1, rotation: Int = -1): Int {
            return position.y + ((if (rotation == 1 || rotation == 3) sizeX else sizeY) - 1) / 2
        }
    }
}