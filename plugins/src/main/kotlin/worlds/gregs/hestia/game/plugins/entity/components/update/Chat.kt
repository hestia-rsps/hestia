package worlds.gregs.hestia.game.plugins.entity.components.update

import com.artemis.Component

class Chat() : Component() {

    constructor(message: String) : this() {
        this.message = message
    }

    var message: String? = null
}