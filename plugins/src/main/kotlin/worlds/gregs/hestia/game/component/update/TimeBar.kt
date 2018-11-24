package worlds.gregs.hestia.game.component.update

import com.artemis.Component
import com.artemis.Entity
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class TimeBar : Component() {
    var full = false
    var exponentialDelay = 0
    var delay = 0
    var increment = 0
}

fun Entity.time(full: Boolean = false, exponent: Int = 0, delay: Int = 0, increment: Int = 1) {
    val time = TimeBar()
    time.apply {
        this.full = full
        this.exponentialDelay = exponent
        this.delay = delay
        this.increment = increment
    }
    edit().add(time)
}