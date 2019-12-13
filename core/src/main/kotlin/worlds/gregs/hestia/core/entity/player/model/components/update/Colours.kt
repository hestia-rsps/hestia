package worlds.gregs.hestia.core.entity.player.model.components.update

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Colours() : Component() {

    constructor(colours: IntArray) : this() {
        this.colours = colours
    }

    var colours: IntArray? = null
}