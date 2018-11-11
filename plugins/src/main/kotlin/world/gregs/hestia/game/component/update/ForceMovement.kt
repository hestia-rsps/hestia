package world.gregs.hestia.game.component.update

import com.artemis.Component
import com.artemis.Entity
import com.artemis.annotations.PooledWeaver
import world.gregs.hestia.game.component.map.Position
import world.gregs.hestia.game.component.movement.move
import world.gregs.hestia.game.events.schedule
import world.gregs.hestia.services.getComponent

@PooledWeaver
class ForceMovement : Component() {
    /*
        Two uses

        Instant movement (second position and delay left null)
        Delayed/Double movement (all in use)
     */
    var firstPosition: Position? = null
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

/*
    Delayed single movement
 */
fun Entity.force(initialDelay: Int, position: Position, duration: Int, direction: Int) {
    val current = getComponent(Position::class) ?: return
    force(current, initialDelay, direction, position, duration)
}

/*
    Single or double movement
 */
fun Entity.force(position: Position, delay: Int, direction: Int, secondPosition: Position? = null, secondDelay: Int = 0, move: Boolean = true) {
    if(delay <= 0 || secondDelay < 0) {
        throw IllegalArgumentException("Force movement delay must be positive.")
    }
    if(secondPosition != null && delay >= secondDelay) {
        throw IllegalArgumentException("First delay must be less than the second")
    }
    //Add force movement
    val movement = ForceMovement()
    movement.apply {
        this.firstPosition = position
        this.firstDelay = delay
        this.secondPosition = secondPosition
        this.secondDelay = secondDelay
        this.direction = direction
    }
    edit().add(movement)

    //Moves player to finishing position after force is complete
    if(move) {
        schedule(Math.max(delay, secondDelay) - 1, 0) {
            move(secondPosition ?: position)
            stop()
        }
    }
}