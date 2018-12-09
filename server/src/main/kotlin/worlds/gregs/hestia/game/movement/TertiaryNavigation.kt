package worlds.gregs.hestia.game.movement

import worlds.gregs.hestia.api.collision.Collision
import worlds.gregs.hestia.api.movement.TerrainNavigation
import worlds.gregs.hestia.game.update.Direction

/**
 * TertiaryNavigation
 * Checks terrain around a coordinate of any size
 */
class TertiaryNavigation(override val collision: Collision?): TerrainNavigation {

    override fun traversable(direction: Direction, x: Int, y: Int, width: Int, height: Int, deltaX: Int, deltaY: Int): Boolean {
        //Entity source coordinate is south-west so offset by width/height if moving north or east
        val offsetX = if (deltaX == 1) width else deltaX
        val offsetY = if (deltaY == 1) height else deltaY
        if (!direction.isDiagonal()) {
            //Check clipping in direction of movement
            if (!free(x + offsetX, y + offsetY, direction.getSouthCornerClippingMask())
                    //If moving north or east check clipping one tile outside the entity in direction of movement (if south or west only need -1 from source)
                    || !free(x + if (deltaX == -1) -1 else width + (deltaX - 1), y + if (deltaY == -1) -1 else height + (deltaY - 1), direction.getNorthCornerClippingMask())) {
                return false
            }

            //Check the entire side of the entity which is moving (if horizontally check vertically and visa versa)
            for (sizeOffset in 1 until (if (deltaY == 0) height else width) - 1) {
                //If moving north or east account for entity size, if south or west only check -1, otherwise use the sizeOffset
                if (!free(x + if (deltaX == 1) width else if (deltaX == -1) -1 else sizeOffset, y + if (deltaY == 1) height else if (deltaY == -1) -1 else sizeOffset, direction.getSurroundClippingMask())) {
                    return false
                }
            }
        } else {
            //Check clipping in direction of movement
            if (!free(x + offsetX, y + offsetY, direction.getClippingMask())) {
                return false
            }

            //Check horizontal side so we're not blocked from moving vertically
            for (sizeOffsetX in 1 until width) {
                //Check one tile outside of entity (delta can't be 0) for vertical movement
                if (!free(x + sizeOffsetX - if (deltaX == 1) 0 else 1, y + offsetY, direction.getVerticalClear())) {
                    return false
                }
            }

            //Check vertical side so we're not blocked from moving horizontally
            for (sizeOffsetY in 1 until height) {
                //Check one tile outside of entity (delta can't be 0)
                if (!free(x + offsetX, y + sizeOffsetY - if (deltaY == 1) 0 else 1, direction.getHorizontalClear())) {
                    return false
                }
            }
        }
        return true
    }

}