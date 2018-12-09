package worlds.gregs.hestia.game.plugins.movement.components.types

import com.artemis.Component
import com.artemis.Entity
import com.artemis.annotations.PooledWeaver
import worlds.gregs.hestia.game.plugins.core.components.map.Position

@PooledWeaver
class MoveStep() : Component() {

    constructor(x: Int, y: Int, plane: Int = 0) : this() {
        this.x = x
        this.y = y
        this.plane = plane
    }

    var x = -1
    var y = -1
    var plane = -1
}

fun Entity.move(position: Position) {
    move(position.x, position.y, position.plane)
}

fun Entity.move(x: Int, y: Int, plane: Int = 0) {
    edit().add(MoveStep(x, y, plane))
}