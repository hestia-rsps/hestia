package worlds.gregs.hestia.core.world.movement.logic.strategies

import worlds.gregs.hestia.core.world.collision.api.Collision
import worlds.gregs.hestia.core.world.movement.api.RouteStrategy
import java.util.*

class EntityStrategy(override val destinationX: Int, override val destinationY: Int, private val entitySizeX: Int, private val entitySizeY: Int, private val accessBlockFlag: Int = 0) : RouteStrategy {

    override fun exit(currentX: Int, currentY: Int, sizeX: Int, sizeY: Int, clipBaseX: Int, clipBaseY: Int, collision: Collision?): Boolean {
        return checkFilledRectangularInteract(collision, currentX - clipBaseX, currentY - clipBaseY, sizeX, sizeY, destinationX - clipBaseX, destinationY - clipBaseY, entitySizeX, entitySizeY, accessBlockFlag)
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


        /**
         * Check's if we can interact filled rectangular (Might be ground object or
         * npc or player etc) from current position.
         */
        fun checkFilledRectangularInteract(collision: Collision?, currentX: Int, currentY: Int, sizeX: Int, sizeY: Int, targetX: Int, targetY: Int, targetSizeX: Int, targetSizeY: Int, accessBlockFlag: Int): Boolean {
            val srcEndX = currentX + sizeX
            val srcEndY = currentY + sizeY
            val destEndX = targetX + targetSizeX
            val destEndY = targetY + targetSizeY
            val type = when {
                currentX == destEndX && accessBlockFlag and 0x2 == 0 -> 0//Right side
                targetX == srcEndX && accessBlockFlag and 0x8 == 0 -> 1//Left side
                currentY == destEndY && accessBlockFlag and 0x1 == 0 -> 2//Above
                targetY == srcEndY && accessBlockFlag and 0x4 == 0 -> 3//Below
                else -> -1
            }

            if (type == -1 || collision == null) {
                return false
            }

            if (type < 2) {
                var maxY = currentY.coerceAtLeast(targetY)
                val minY = srcEndY.coerceAtMost(destEndY)
//                val data = clip[if(type == 0) targetX else destEndX - 1]
                val flag = if (type == 0) 0x8 else 0x80
                while (maxY < minY) {
//                    if (data[maxY++] and flag == 0) {
                    if (collision.collides(if (type == 0) targetX else destEndX - 1, maxY++, flag)) {
                        return true
                    }
                }
            } else {
                var maxX = currentX.coerceAtLeast(targetX)
                val minX = srcEndX.coerceAtMost(destEndX)
                val clipY = if (type == 2) destEndY - 1 else targetY
                val flag = if (type == 2) 0x2 else 0x20
                while (maxX < minX) {
//                    if (clip[maxX++][clipY] and flag == 0) {
                    if (collision.collides(maxX++, clipY, flag)) {
                        return true
                    }
                }
            }
            return false
        }
    }
}