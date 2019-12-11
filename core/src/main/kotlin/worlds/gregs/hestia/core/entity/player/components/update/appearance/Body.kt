package worlds.gregs.hestia.core.entity.player.components.update.appearance

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Body() : Component() {

    constructor(look: IntArray) : this() {
        this.look = look
    }

    var look: IntArray? = null
}