package worlds.gregs.hestia.game.plugins.entity.components.update

import com.artemis.Component
import com.artemis.Entity
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class TimeBar() : Component() {

    constructor(full: Boolean = false, exponent: Int = 0, delay: Int = 0, increment: Int = 1) : this() {
        this.full = full
        this.exponentialDelay = exponent
        this.delay = delay
        this.increment = increment
    }

    var full = false
    var exponentialDelay = 0
    var delay = 0
    var increment = 0
}