package worlds.gregs.hestia.game.component.movement

import com.artemis.Component
import com.artemis.Entity
import com.artemis.annotations.PooledWeaver
import worlds.gregs.hestia.game.component.map.Position

@PooledWeaver
class Move : Component() {
    var x = -1
    var y = -1
    var plane = -1
}

fun Entity.move(position: Position) {
    move(position.x, position.y, position.plane)
}

fun Entity.move(x: Int, y: Int, plane: Int = 0) {
    val move = Move()
    move.x = x
    move.y = y
    move.plane = plane
    edit().add(move)
}