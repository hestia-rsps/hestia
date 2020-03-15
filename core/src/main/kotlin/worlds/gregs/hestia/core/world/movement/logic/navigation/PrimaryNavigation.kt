package worlds.gregs.hestia.core.world.movement.logic.navigation

import worlds.gregs.hestia.core.display.update.model.Direction
import worlds.gregs.hestia.core.world.collision.api.Collision
import worlds.gregs.hestia.core.world.collision.model.CollisionFlag.block
import worlds.gregs.hestia.core.world.movement.api.TerrainNavigation

/**
 * PrimaryNavigation
 * Checks terrain around a coordinate optimised for 1x1
 */
class PrimaryNavigation(override val collision: Collision?) : TerrainNavigation {

    override fun traversable(direction: Direction, x: Int, y: Int, width: Int, height: Int, deltaX: Int, deltaY: Int): Boolean {
        //If not diagonal just check the collision
        return free(x + deltaX, y + deltaY, direction.block()) &&
                //If diagonal check the horizontal and vertical tiles too (corners)
                (!direction.isDiagonal() || (free(x + deltaX, y, direction.horizontal().block()) && free(x, y + deltaY, direction.vertical().block())))
    }

}