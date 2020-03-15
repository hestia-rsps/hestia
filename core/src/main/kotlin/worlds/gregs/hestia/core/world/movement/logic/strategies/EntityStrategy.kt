package worlds.gregs.hestia.core.world.movement.logic.strategies

import worlds.gregs.hestia.core.display.update.model.Direction
import worlds.gregs.hestia.core.world.collision.api.Collision
import worlds.gregs.hestia.core.world.collision.model.CollisionFlag.flag
import worlds.gregs.hestia.core.world.movement.api.RouteStrategy
import java.util.*

class EntityStrategy(override val destinationX: Int, override val destinationY: Int, private val entitySizeX: Int, private val entitySizeY: Int, private val accessBlockFlag: Int = 0) : RouteStrategy {

    override fun exit(currentX: Int, currentY: Int, sizeX: Int, sizeY: Int, collision: Collision?): Boolean {
        return checkFilledRectangularInteract(collision, sizeX, destinationY, sizeY, currentX, destinationX, accessBlockFlag, entitySizeY, currentY, entitySizeX)
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


        fun checkFilledRectangularInteract(collision: Collision?, sizeX: Int, targetY: Int, sizeY: Int, currentX: Int, targetX: Int, accessBlockFlag: Int, targetSizeY: Int, currentY: Int, targetSizeX: Int): Boolean {
            if (collision == null) {
                return false
            }
            val srcEndX = currentX + sizeX
            val srcEndY = currentY + sizeY
            val destEndX = targetX + targetSizeX
            val destEndY = targetY + targetSizeY
            if (currentX == destEndX && accessBlockFlag and 0x2 == 0) {
                val minY = if (targetY < currentY) currentY else targetY
                val maxY = if (destEndY <= srcEndY) destEndY else srcEndY
                for(clipY in minY until maxY) {
                    if (!collision.collides(destEndX - 1, clipY, Direction.EAST.flag())) {
                        return true
                    }
                }
            } else if (targetX == srcEndX && accessBlockFlag and 0x8 == 0) {
                val minY = if (currentY <= targetY) targetY else currentY
                val maxY = if (destEndY <= srcEndY) destEndY else srcEndY
                for(clipY in minY until maxY) {
                    if (!collision.collides(targetX, clipY, Direction.WEST.flag())) {
                        return true
                    }
                }
            } else if (currentY == destEndY && accessBlockFlag and 0x1 == 0) {
                val minX = if (currentX <= targetX) targetX else currentX
                val maxX = if (destEndX <= srcEndX) destEndX else srcEndX
                for(clipX in minX until maxX) {
                    if (!collision.collides(clipX, destEndY - 1, Direction.NORTH.flag())) {
                        return true
                    }
                }
            } else if (targetY == srcEndY && accessBlockFlag and 0x4 == 0) {
                val minX = if (currentX > targetX) currentX else targetX
                val maxX = if (srcEndX >= destEndX) destEndX else srcEndX
                for(clipX in minX until maxX) {
                    if (!collision.collides(clipX, targetY, Direction.SOUTH.flag())) {
                        return true
                    }
                }
            }
            return false
        }
    }
}