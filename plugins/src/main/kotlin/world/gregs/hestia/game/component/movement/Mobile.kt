package world.gregs.hestia.game.component.movement

import com.artemis.Component
import com.artemis.annotations.PooledWeaver
import world.gregs.hestia.game.component.map.Position

@PooledWeaver
class Mobile : Component() {
    var lastX = -1
    var lastY = -1
    var lastPlane = -1
    var lastPosition: Position? = null
}