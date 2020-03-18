package worlds.gregs.hestia.core.display.update.model.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver
import worlds.gregs.hestia.core.entity.entity.model.components.Position

@PooledWeaver
data class ForceMovement(var firstDelay: Int = 0, var secondPosition: Position? = null, var secondDelay: Int = 0, var direction: Int = 0) : Component() {

    constructor(first: Position, delay: Int, second: Position?, secondDelay: Int = 0, direction: Int = 0) : this(delay, second, secondDelay, direction) {
        this.firstPosition = first
    }
    /*
        Two uses

        Instant movement (second position and delay left null)
        Delayed/Double movement (all in use)
     */
    lateinit var firstPosition: Position

    companion object {
        const val NORTH = 0
        const val EAST = 1
        const val SOUTH = 2
        const val WEST = 3
    }
}