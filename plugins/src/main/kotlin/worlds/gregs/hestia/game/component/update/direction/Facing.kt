package worlds.gregs.hestia.game.component.update.direction

import com.artemis.Component
import com.artemis.Entity
import com.artemis.annotations.PooledWeaver
import worlds.gregs.hestia.game.component.map.Position
import worlds.gregs.hestia.game.component.update.direction.Facing.Companion.getFaceX
import worlds.gregs.hestia.game.component.update.direction.Facing.Companion.getFaceY
import worlds.gregs.hestia.game.component.entity.Size
import worlds.gregs.hestia.services.getComponent

@PooledWeaver
class Facing : Component() {
    var x = 0
    var y = -1

    companion object {
        fun getFaceX(position: Position, sizeX: Int, sizeY: Int = -1, rotation: Int = -1): Int {
            return position.x + ((if (rotation == 1 || rotation == 3) sizeY else sizeX) - 1) / 2
        }

        fun getFaceY(position: Position, sizeY: Int, sizeX: Int = -1, rotation: Int = -1): Int {
            return position.y + ((if (rotation == 1 || rotation == 3) sizeX else sizeY) - 1) / 2
        }
    }
}

fun Entity.turn(deltaX: Int, deltaY: Int) {
    val face = Face()
    face.x = deltaX
    face.y = deltaY
    edit().add(face)
}

fun Entity.face(x: Int, y: Int) {
    val size = getComponent(Size::class)
    val position = getComponent(Position::class)!!
    val face = Face()
    face.x = x - getFaceX(position, size?.sizeX ?: 1)
    face.y = y - getFaceY(position, size?.sizeY ?: 1)
    edit().add(face)
}