package worlds.gregs.hestia.api.map

import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.game.entity.components.Position
import worlds.gregs.hestia.game.client.update.block.Direction

/**
 * TileClipping
 * Checks whether the next tile to step on is free (unclipped)
 */
abstract class TileClipping : PassiveSystem() {

    /**
     * Get the mask of the tile at [x], [y], [plane]
     * @param x The x coordinate of the tile
     * @param y The y coordinate of the tile
     * @param plane The plane coordinate of the tile
     * @return The clipping mask value
     */
    abstract fun getMask(x: Int, y: Int, plane: Int): Int

    /**
     * Get's the rotation of the chunk [x], [y], [plane] is in.
     * @param x The x coordinate of the tile
     * @param y The y coordinate of the tile
     * @param plane The plane coordinate of the tile
     * @return The chunks rotation
     */
    abstract fun getRotation(x: Int, y: Int, plane: Int): Int

    /**
     * Check if any adjacent clipping tiles contain obstacles
     * @param dir The direction of movement
     * @param x Start x coordinate
     * @param y Start y coordinate
     * @param plane Start plane coordinate
     * @param width The width of area to check
     * @param height The height of area to check
     */
    abstract fun traversable(dir: Direction, x: Int, y: Int, plane: Int, width: Int, height: Int): Boolean

    /**
     * Check if any adjacent clipping tiles contain obstacles
     * @param dir The direction of movement
     * @param position Start position
     * @param width The width of area to check
     * @param height The height of area to check
     */
    fun traversable(dir: Direction, position: Position, width: Int, height: Int): Boolean {
        return traversable(dir, position.x, position.y, position.plane, width, height)
    }

}