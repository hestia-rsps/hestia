package worlds.gregs.hestia.core.plugins.player.component.update.appearance

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Body() : Component() {

    constructor(look: IntArray) : this() {
        this.look = look
    }

    var look: IntArray? = null
}