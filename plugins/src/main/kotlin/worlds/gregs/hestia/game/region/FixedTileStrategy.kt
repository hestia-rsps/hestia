package worlds.gregs.hestia.game.region

import worlds.gregs.hestia.game.path.RouteStrategy
import java.util.*

class FixedTileStrategy(override val destinationX: Int, override val destinationY: Int) : RouteStrategy {

    override fun exit(currentX: Int, currentY: Int, sizeX: Int, sizeY: Int, clip: Array<IntArray>, clipBaseX: Int, clipBaseY: Int): Boolean {
        return currentX == destinationX && currentY == destinationY
    }

    override val sizeX: Int
        get() = 1

    override val sizeY: Int
        get() = 1

    override fun equals(other: Any?): Boolean {
        val strategy = other as? FixedTileStrategy ?: return false
        return destinationX == strategy.destinationX && destinationY == strategy.destinationY
    }

    override fun hashCode(): Int {
        return Objects.hash(destinationX, destinationY)
    }
}