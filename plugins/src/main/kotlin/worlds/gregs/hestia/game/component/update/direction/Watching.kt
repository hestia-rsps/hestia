package worlds.gregs.hestia.game.component.update.direction

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Watching : Component() {
    var clientIndex = 0//Entity index
}