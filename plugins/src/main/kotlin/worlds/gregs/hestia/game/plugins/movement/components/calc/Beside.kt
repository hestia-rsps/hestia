package worlds.gregs.hestia.game.plugins.movement.components.calc

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Beside() : Component() {

    constructor(x: Int, y: Int, max: Int = -1, width: Int = 1, height: Int = 1, check: Boolean = true, calculate: Boolean = false, beside: Boolean = false) : this() {
        this.x = x
        this.y = y
        this.max = max
        this.sizeX = width
        this.sizeY = height
        this.check = check
        this.calculate = calculate
        this.beside = beside
    }

    var x = -1
    var y = -1
    var max = -1
    var sizeX = 1
    var sizeY = 1
    var check = true
    var calculate = false
    var beside = false
}