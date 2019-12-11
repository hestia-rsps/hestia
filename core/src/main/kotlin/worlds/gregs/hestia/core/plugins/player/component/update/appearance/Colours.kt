package worlds.gregs.hestia.core.plugins.player.component.update.appearance

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Colours() : Component() {

    constructor(colours: IntArray) : this() {
        this.colours = colours
    }

    var colours: IntArray? = null
}