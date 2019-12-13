package worlds.gregs.hestia.core.display.update.model.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class ColourOverlay() : Component() {

    constructor(delay: Int, duration: Int, colour: Int) : this() {
        this.delay = delay
        this.duration = duration
        this.colour = colour
    }

    var delay = 0
    var duration = 0
    var colour = 0
}