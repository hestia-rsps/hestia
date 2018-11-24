package worlds.gregs.hestia.game.plugins.entity.components.update.direction

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Face : Component() {
    var x = 0
    var y = 0
}