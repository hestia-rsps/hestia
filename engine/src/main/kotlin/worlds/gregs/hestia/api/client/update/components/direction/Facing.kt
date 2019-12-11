package worlds.gregs.hestia.api.client.update.components.direction

import com.artemis.Component
import com.artemis.annotations.PooledWeaver
import worlds.gregs.hestia.game.entity.components.Position

@PooledWeaver
class Facing : Component() {
    companion object {
        fun getFaceX(position: Position, sizeX: Int, sizeY: Int = -1, rotation: Int = -1): Int {
            return position.x + ((if (rotation == 1 || rotation == 3) sizeY else sizeX) - 1) / 2
        }

        fun getFaceY(position: Position, sizeY: Int, sizeX: Int = -1, rotation: Int = -1): Int {
            return position.y + ((if (rotation == 1 || rotation == 3) sizeX else sizeY) - 1) / 2
        }
    }
}