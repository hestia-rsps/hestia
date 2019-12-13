package worlds.gregs.hestia.core.world.movement.model.components.calc

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Step() : Component() {

    constructor(x: Int, y: Int, max: Int = -1, check: Boolean = true) : this() {
        this.x = x
        this.y = y
        this.max = max
        this.check = check
    }

    var x = -1
    var y = -1
    var max = -1
    var check = true
}