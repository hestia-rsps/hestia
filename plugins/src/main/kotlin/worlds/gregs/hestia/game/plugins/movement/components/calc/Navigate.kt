package worlds.gregs.hestia.game.plugins.movement.components.calc

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Navigate() : Component() {

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