package worlds.gregs.hestia.game.plugins.movement.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Walk : Component() {
    var direction = -1
}