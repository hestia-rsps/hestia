package worlds.gregs.hestia.core.entity.player.model.components.update

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Body() : Component() {

    constructor(look: IntArray) : this() {
        this.look = look
    }

    var look: IntArray? = null
}