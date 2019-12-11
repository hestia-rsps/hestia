package worlds.gregs.hestia.core.plugins.movement.strategies

import worlds.gregs.hestia.api.collision.Collision
import worlds.gregs.hestia.api.movement.RouteStrategy
import worlds.gregs.hestia.core.plugins.movement.strategies.EntityStrategy.Companion.checkFilledRectangularInteract
import java.util.*

class FloorItemStrategy(override val destinationX: Int, override val destinationY: Int) : RouteStrategy {

    override fun exit(currentX: Int, currentY: Int, sizeX: Int, sizeY: Int, clipBaseX: Int, clipBaseY: Int, collision: Collision?): Boolean {
        return checkFilledRectangularInteract( collision, currentX - clipBaseX, currentY - clipBaseY, sizeX, sizeY, destinationX - clipBaseX, destinationY - clipBaseY, 1, 1, 0)
    }

    override val sizeX: Int
        get() = 1

    override val sizeY: Int
        get() = 1

    override fun equals(other: Any?): Boolean {
        val strategy = other as? FloorItemStrategy ?: return false
        return destinationX == strategy.destinationX && destinationY == strategy.destinationY
    }

    override fun hashCode(): Int {
        return Objects.hash(destinationX, destinationY)
    }
}