package worlds.gregs.hestia.core.display.update.model.components.direction

import com.artemis.Component
import com.artemis.annotations.PooledWeaver
import worlds.gregs.hestia.core.entity.entity.model.components.Position

@PooledWeaver
class FaceUpdate : Component() {

    companion object {
        fun getFaceX(position: Position, sizeX: Int, sizeY: Int = -1, rotation: Int = -1): Int {
            return position.x + ((if (rotation == 1 || rotation == 3) sizeY else sizeX) - 1) / 2
        }

        fun getFaceY(position: Position, sizeY: Int, sizeX: Int = -1, rotation: Int = -1): Int {
            return position.y + ((if (rotation == 1 || rotation == 3) sizeX else sizeY) - 1) / 2
        }
    }
}