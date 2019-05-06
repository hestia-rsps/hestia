package worlds.gregs.hestia.api.client.update.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class ForceChat() : Component() {

    constructor(message: String) : this() {
        this.message = message
    }

    var message: String? = null
}