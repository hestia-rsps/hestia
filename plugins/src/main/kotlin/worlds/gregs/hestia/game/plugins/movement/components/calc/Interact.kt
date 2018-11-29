package worlds.gregs.hestia.game.plugins.movement.components.calc

import com.artemis.Component
import com.artemis.Entity
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Interact() : Component() {

    constructor(x: Int, y: Int, max: Int = -1, size: Int = 1, check: Boolean = true, calculate: Boolean = true) : this() {
        this.x = x
        this.y = y
        this.max = max
        this.sizeX = size
        this.sizeY = size
        this.check = check
        this.calculate = calculate
    }

    var x = -1
    var y = -1
    var max = -1
    var sizeX = 1
    var sizeY = 1
    var check = true
    var calculate = true
}

fun Entity.interact(x: Int, y: Int, max: Int = -1, size: Int = 1, check: Boolean = true, calculate: Boolean = true) {
    edit().add(Interact(x, y, max, size, check, calculate))
}