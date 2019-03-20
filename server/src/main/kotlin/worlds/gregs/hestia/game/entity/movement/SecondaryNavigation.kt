package worlds.gregs.hestia.game.entity.movement

import worlds.gregs.hestia.api.collision.Collision
import worlds.gregs.hestia.api.movement.TerrainNavigation
import worlds.gregs.hestia.game.update.Direction

/**
 * SecondaryNavigation
 * Checks terrain around a coordinate optimised for 2x2
 */
class SecondaryNavigation(override val collision: Collision?) : TerrainNavigation {

    override fun traversable(direction: Direction, x: Int, y: Int, width: Int, height: Int, deltaX: Int, deltaY: Int): Boolean {
        val offsetX = if (deltaX == 1) width else deltaX
        val offsetY = if (deltaY == 1) height else deltaY

        return if (!direction.isDiagonal()) {
            //Check clipping in direction of movement
            free(x + offsetX, y + offsetY, direction.getSouthCornerClippingMask())
                    //Check clipping of north-east corner of the entity to make sure they will fit (optimised for 2x2)
                    && free(x + if (deltaX == 0) 1 else if (deltaX == 1) width else -1, y + if (deltaY == 0) 1 else if (deltaY == 1) height else -1, direction.getNorthCornerClippingMask())
        } else {
            //Check clipping in direction of movement
            free(x + offsetX, y + offsetY, direction.getClippingMask())
                    //Check the top side of the entity
                    && free(x + offsetX, y + if (deltaY == -1) 0 else deltaY, direction.getHorizontalClear())
                    //Check the right side of the entity
                    && free(x + if (deltaX == -1) 0 else deltaX, y + offsetY, direction.getVerticalClear())
        }
    }
}