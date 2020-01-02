package worlds.gregs.hestia.core.world.movement.logic.strategies

import worlds.gregs.hestia.core.world.collision.api.Collision
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
            if (currentX != destEndX || 0x2 and accessBlockFlag != 0) {
                if (srcEndX != targetX || accessBlockFlag and 0x8 != 0) {
                    if (destEndY == currentY && 0x1 and accessBlockFlag == 0) {
                        var clipX = if (currentX <= targetX) targetX else currentX
                        val maxX = if (destEndX <= srcEndX) destEndX else srcEndX
                        while (clipX < maxX) {
                            if (collision.collides(-clipBaseX + clipX, -clipBaseY + destEndY - 1, 0x2)) {
                                return true
                            }
                            clipX++
                        }
                    } else if (srcEndY == targetY && 0x4 and accessBlockFlag == 0) {
                        var clipX = if (currentX > targetX) currentX else targetX
                        val maxX = if (srcEndX >= destEndX) destEndX else srcEndX
                        while (clipX < maxX) {
                            if (collision.collides(-clipBaseX + clipX, targetY - clipBaseY, 0x20)) {
                                return true
                            }
                            clipX++
                        }
                    }
                } else {
                    var clipY = if (currentY <= targetY) targetY else currentY
                    val maxY = if (destEndY <= srcEndY) destEndY else srcEndY
                    while (clipY < maxY) {
                        if (collision.collides(targetX - clipBaseX, clipY + -clipBaseY, 0x80)) {
                            return true
                        }
                        clipY++
                    }
                }
            } else {
                var clipY = if (targetY < currentY) currentY else targetY
                val maxY = if (destEndY <= srcEndY) destEndY else srcEndY
                while (clipY < maxY) {
                    if (collision.collides(destEndX + (-1 + -clipBaseX), -clipBaseY + clipY, 0x8)) {
                        return true
                    }
                    clipY++
                }
            }
            return false
        }
    }
}