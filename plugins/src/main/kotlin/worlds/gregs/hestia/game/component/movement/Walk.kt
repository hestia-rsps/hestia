package worlds.gregs.hestia.game.component.movement

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Walk : Component() {
    var direction = -1
}