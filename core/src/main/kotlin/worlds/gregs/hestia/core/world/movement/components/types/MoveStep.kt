package worlds.gregs.hestia.core.world.movement.components.types

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

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