package worlds.gregs.hestia.core.world.movement.strategies

import worlds.gregs.hestia.api.collision.Collision
import worlds.gregs.hestia.api.movement.RouteStrategy
import java.util.*

class ObjectStrategy(x: Int, y: Int) : RouteStrategy {

    override fun exit(currentX: Int, currentY: Int, sizeX: Int, sizeY: Int, clipBaseX: Int, clipBaseY: Int, collision: Collision?): Boolean {
        return currentX == destinationX && currentY == destinationY
    }
    override val destinationX = x

    override val destinationY = y

    override val sizeX = 1

    override val sizeY = 1

    override fun equals(other: Any?): Boolean {
        val strategy = other as? ObjectStrategy ?: return false
        return destinationX == strategy.destinationX && destinationY == strategy.destinationY
    }

    override fun hashCode(): Int {
        return Objects.hash(destinationX, destinationY)
    }
}