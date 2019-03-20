package worlds.gregs.hestia.game.update.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver
import worlds.gregs.hestia.game.update.Direction

@PooledWeaver
class RunStep : Component() {
    var direction = Direction.NONE
}