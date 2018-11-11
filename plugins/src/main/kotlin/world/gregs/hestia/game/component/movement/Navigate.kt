package world.gregs.hestia.game.component.movement

import com.artemis.Component
import com.artemis.Entity
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Navigate : Component() {
    var x = -1
    var y = -1
    var max = -1
    var check = true
}

fun Entity.navigate(x: Int, y: Int, max: Int = -1, check: Boolean = true) {
    val move = Navigate()
    move.x = x
    move.y = y
    move.max = max
    move.check = check
    edit().add(move)
}