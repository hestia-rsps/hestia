package worlds.gregs.hestia.game.movement

import worlds.gregs.hestia.api.collision.Collision
import worlds.gregs.hestia.api.movement.TerrainNavigation
import worlds.gregs.hestia.game.update.Direction

/**
 * PrimaryNavigation
 * Checks terrain around a coordinate optimised for 1x1
 */
class PrimaryNavigation(override val collision: Collision?) : TerrainNavigation {

    override fun traversable(direction: Direction, x: Int, y: Int, width: Int, height: Int, deltaX: Int, deltaY: Int): Boolean {
        //If not diagonal just check the clipping
        return free(x + deltaX, y + deltaY, direction.getClippingMask()) &&
                //If diagonal check the horizontal and vertical tiles too (corners)
                (!direction.isDiagonal() || (free(x + deltaX, y, direction.getHorizontalMask()) && free(x, y + deltaY, direction.getVerticalMask())))
    }

}