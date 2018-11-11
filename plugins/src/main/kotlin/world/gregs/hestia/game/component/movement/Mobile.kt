package world.gregs.hestia.game.component.movement

import com.artemis.Component
import com.artemis.annotations.PooledWeaver
import world.gregs.hestia.game.component.map.Position

@PooledWeaver
class Mobile : Component() {
    var lastPosition: Position? = null
}