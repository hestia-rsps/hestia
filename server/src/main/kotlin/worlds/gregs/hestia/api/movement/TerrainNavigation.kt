package worlds.gregs.hestia.api.movement

import worlds.gregs.hestia.api.collision.Collision
import worlds.gregs.hestia.game.update.Direction

interface TerrainNavigation {

    val collision: Collision?
    /**
     * Checks whether movement of an entity of size [width] [height] from [x], [y] in direction [direction]
     * Note: [deltaX], [deltaY] are non-kotlin optional use to performance benefits.
     * @param direction The direction of proposed movement
     * @param x The initial x coordinate
     * @param y The initial y coordinate
     * @param width The width of the entity to move
     * @param height The height of the entity to move
     * @param deltaX The direction delta x
     * @param deltaY The direction delta y
     * @return Whether traversal is possible
     */
    fun traversable(direction: Direction, x: Int, y: Int, width: Int, height: Int, deltaX: Int, deltaY: Int): Boolean

    /**
     * Checks whether any objects at [x], [y] collide with [mask] type
     * @param x The x coordinate to check
     * @param y The y coordinate to check
     * @param mask The masks of object types to collide with
     */
    fun free(x: Int, y: Int, mask: Int): Boolean {
        return if(collision == null) false else !collision!!.collides(x, y, mask)
    }
}