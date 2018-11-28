package worlds.gregs.hestia.game.plugins.movement.components

import com.artemis.Component
import com.artemis.Entity
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Interact : Component() {
    var x = -1
    var y = -1
    var max = -1
    var sizeX = 1
    var sizeY = 1
    var check = true
    var calculate = true
}

fun Entity.interact(x: Int, y: Int, max: Int = -1, size: Int = 1, check: Boolean = true, calculate: Boolean = true) {
    val move = Interact()
    move.x = x
    move.y = y
    move.max = max
    move.sizeX = size
    move.sizeY = size
    move.check = check
    move.calculate = calculate
    edit().add(move)
}