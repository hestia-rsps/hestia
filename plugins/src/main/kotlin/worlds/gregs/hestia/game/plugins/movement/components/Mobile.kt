package worlds.gregs.hestia.game.plugins.movement.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Mobile : Component() {
    var lastX = -1
    var lastY = -1
    var lastPlane = -1
}