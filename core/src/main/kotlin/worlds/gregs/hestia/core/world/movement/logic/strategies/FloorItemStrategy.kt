package worlds.gregs.hestia.core.world.movement.logic.strategies

import worlds.gregs.hestia.core.world.collision.api.Collision
import worlds.gregs.hestia.core.world.movement.api.RouteStrategy
import java.util.*

class FloorItemStrategy(override val destinationX: Int, override val destinationY: Int) : RouteStrategy {

    override fun exit(currentX: Int, currentY: Int, sizeX: Int, sizeY: Int, collision: Collision?): Boolean {
        return checkRectangleInteract(currentX, currentY, sizeX, sizeY, destinationX, destinationY, 1, 1)
    }

    override val sizeX = 1

    override val sizeY = 1

    override fun equals(other: Any?): Boolean {
        val strategy = other as? FloorItemStrategy ?: return false
        return destinationX == strategy.destinationX && destinationY == strategy.destinationY
    }

    override fun hashCode(): Int {
        return Objects.hash(destinationX, destinationY)
    }

    companion object {
        fun checkRectangleInteract(currentX: Int, currentY: Int, sizeX: Int, sizeY: Int, targetX: Int, targetY: Int, targetSizeX: Int, targetSizeY: Int): Boolean {
            if (targetX + targetSizeX <= currentX || targetX >= currentX + sizeX) {
                return false
            }
            return currentY < targetY + targetSizeY && sizeY + currentY > targetY
        }
    }
}