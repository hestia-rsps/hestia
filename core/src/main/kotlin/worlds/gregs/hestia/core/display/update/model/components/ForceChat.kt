package worlds.gregs.hestia.core.display.update.model.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class ForceChat() : Component() {

    constructor(message: String) : this() {
        this.message = message
    }

    var message: String? = null
}