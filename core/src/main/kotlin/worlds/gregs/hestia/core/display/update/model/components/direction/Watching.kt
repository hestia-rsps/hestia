package worlds.gregs.hestia.core.display.update.model.components.direction

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Watching : Component() {
    var clientIndex = 0//Entity index
}