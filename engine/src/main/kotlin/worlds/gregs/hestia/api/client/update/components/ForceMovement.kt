package worlds.gregs.hestia.api.client.update.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver
import worlds.gregs.hestia.game.entity.components.Position

@PooledWeaver
class ForceMovement() : Component() {

    constructor(first: Position, delay: Int, second: Position?, secondDelay: Int, direction: Int) : this() {
        this.firstPosition = first
        this.firstDelay = delay
        this.secondPosition = second
        this.secondDelay = secondDelay
        this.direction = direction
    }
    /*
        Two uses

        Instant movement (second position and delay left null)
        Delayed/Double movement (all in use)
     */
    lateinit var firstPosition: Position
    var firstDelay: Int = 0
    var secondPosition: Position? = null
    var secondDelay: Int = 0
    var direction: Int = 0

    companion object {
        const val NORTH = 0
        const val EAST = 1
        const val SOUTH = 2
        const val WEST = 3
    }
}