package worlds.gregs.hestia.core.world.movement.logic.strategies

import worlds.gregs.hestia.core.world.collision.api.Collision
import worlds.gregs.hestia.core.world.movement.api.RouteStrategy
import java.util.*

class FixedTileStrategy(override val destinationX: Int, override val destinationY: Int) : RouteStrategy {

    override fun exit(currentX: Int, currentY: Int, sizeX: Int, sizeY: Int, collision: Collision?): Boolean {
        return currentX == destinationX && currentY == destinationY
    }

    override val sizeX = 1

    override val sizeY = 1

    override fun equals(other: Any?): Boolean {
        val strategy = other as? FixedTileStrategy ?: return false
        return destinationX == strategy.destinationX && destinationY == strategy.destinationY
    }

    override fun hashCode(): Int {
        return Objects.hash(destinationX, destinationY)
    }
}