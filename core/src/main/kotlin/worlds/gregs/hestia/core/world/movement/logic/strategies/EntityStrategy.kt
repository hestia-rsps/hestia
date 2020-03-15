package worlds.gregs.hestia.core.world.movement.logic.strategies

import worlds.gregs.hestia.core.display.update.model.Direction
import worlds.gregs.hestia.core.world.collision.api.Collision
import worlds.gregs.hestia.core.world.collision.model.CollisionFlag.flag
import worlds.gregs.hestia.core.world.movement.api.RouteStrategy
import java.util.*

class EntityStrategy(override val destinationX: Int, override val destinationY: Int, private val entitySizeX: Int, private val entitySizeY: Int, private val accessBlockFlag: Int = 0) : RouteStrategy {

    override fun exit(currentX: Int, currentY: Int, sizeX: Int, sizeY: Int, clipBaseX: Int, clipBaseY: Int, collision: Collision?): Boolean {
        return checkFilledRectangularInteract(collision, sizeX, destinationY, sizeY, currentX, destinationX, accessBlockFlag, entitySizeY, currentY, entitySizeX, clipBaseX, clipBaseY)
    }

    override val sizeX: Int
        get() = 1

    override val sizeY: Int
        get() = 1

    override fun equals(other: Any?): Boolean {
        val strategy = other as? EntityStrategy ?: return false
        return destinationX == strategy.destinationX && destinationY == strategy.destinationY
    }

    override fun hashCode(): Int {
        return Objects.hash(destinationX, destinationY)
    }

    companion object {


        fun checkFilledRectangularInteract(collision: Collision?, sizeX: Int, targetY: Int, sizeY: Int, currentX: Int, targetX: Int, accessBlockFlag: Int, targetSizeY: Int, currentY: Int, targetSizeX: Int, clipBaseX: Int, clipBaseY: Int): Boolean {
            if (collision == null) {
                return false
            }
            val srcEndX = sizeX + currentX
            val srcEndY = sizeY + currentY
            val destEndX = targetSizeX + targetX
            val destEndY = targetSizeY + targetY
            if (currentX != destEndX || Direction.NORTH.flag() and accessBlockFlag != 0) {
                if (srcEndX != targetX || accessBlockFlag and Direction.EAST.flag() != 0) {
                    if (destEndY == currentY && Direction.NORTH_WEST.flag() and accessBlockFlag == 0) {
                        var clipX = if (currentX <= targetX) targetX else currentX
                        val maxX = if (destEndX <= srcEndX) destEndX else srcEndX
                        while (clipX < maxX) {
                            if (collision.collides(clipX- clipBaseX, destEndY - (clipBaseY + 1), Direction.NONE.flag())) {
                                return true
                            }
                            clipX++
                        }
                    } else if (srcEndY == targetY && Direction.NORTH_EAST.flag() and accessBlockFlag == 0) {
                        var clipX = if (currentX > targetX) currentX else targetX
                        val maxX = if (srcEndX >= destEndX) destEndX else srcEndX
                        while (clipX < maxX) {
                            if (collision.collides(clipX - clipBaseX , targetY - clipBaseY, Direction.SOUTH.flag())) {
                                return true
                            }
                            clipX++
                        }
                    }
                } else {
                    var clipY = if (currentY <= targetY) targetY else currentY
                    val maxY = if (destEndY <= srcEndY) destEndY else srcEndY
                    while (clipY < maxY) {
                        if (collision.collides(targetX - clipBaseX, clipY - clipBaseY, Direction.WEST.flag())) {
                            return true
                        }
                        clipY++
                    }
                }
            } else {
                var clipY = if (targetY < currentY) currentY else targetY
                val maxY = if (destEndY <= srcEndY) destEndY else srcEndY
                while (clipY < maxY) {
                    if (collision.collides(destEndX - (clipBaseX + 1), clipY - clipBaseY, Direction.EAST.flag())) {
                        return true
                    }
                    clipY++
                }
            }
            return false
        }
    }
}