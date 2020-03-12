package worlds.gregs.hestia.core.world.movement.logic.navigation

import worlds.gregs.hestia.core.display.update.model.Direction
import worlds.gregs.hestia.core.world.collision.api.Collision
import worlds.gregs.hestia.core.world.collision.model.CollisionFlags.block
import worlds.gregs.hestia.core.world.collision.model.CollisionFlags.clear
import worlds.gregs.hestia.core.world.movement.api.TerrainNavigation

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
            free(x + offsetX, y + offsetY, getNorthCorner(direction).block())
                    //Check clipping of north-east corner of the entity to make sure they will fit (optimised for 2x2)
                    && free(x + if (deltaX == 0) 1 else if (deltaX == 1) width else -1, y + if (deltaY == 0) 1 else if (deltaY == 1) height else -1, getSouthCorner(direction).block())
        } else {
            //Check clipping in direction of movement
            free(x + offsetX, y + offsetY, direction.block())
                    //Check the top side of the entity
                    && free(x + offsetX, y + if (deltaY == -1) 0 else deltaY, direction.horizontal().clear())
                    //Check the right side of the entity
                    && free(x + if (deltaX == -1) 0 else deltaX, y + offsetY, direction.vertical().clear())
        }
    }

    companion object {
        fun getNorthCorner(direction: Direction): Direction {
            return when (direction) {
                Direction.EAST -> Direction.NORTH_EAST
                Direction.WEST -> Direction.NORTH_WEST
                Direction.NORTH -> Direction.NORTH_EAST
                Direction.SOUTH -> Direction.SOUTH_EAST
                else -> Direction.NONE
            }
        }

        fun getSouthCorner(direction: Direction): Direction {
            return when (direction) {
                Direction.EAST -> Direction.SOUTH_EAST
                Direction.WEST -> Direction.SOUTH_WEST
                Direction.NORTH -> Direction.NORTH_WEST
                Direction.SOUTH -> Direction.SOUTH_WEST
                else -> Direction.NONE
            }
        }
    }
}