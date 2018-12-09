package worlds.gregs.hestia.game.plugins.entity.components.update

import com.artemis.Component
import com.artemis.Entity
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class ForceChat() : Component() {

    constructor(message: String) : this() {
        this.message = message
    }

    var message: String? = null
}