package worlds.gregs.hestia.core.world.map.api

import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.core.display.update.model.Direction
import worlds.gregs.hestia.core.entity.entity.model.components.Position

/**
 * TileCollision
 * Checks whether the next tile to step on is free
 */
abstract class TileCollision : PassiveSystem() {

    /**
     * Get's the rotation of the chunk [x], [y], [plane] is in.
     * @param x The x coordinate of the tile
     * @param y The y coordinate of the tile
     * @param plane The plane coordinate of the tile
     * @return The chunks rotation
     */
    abstract fun getRotation(x: Int, y: Int, plane: Int): Int

    /**
     * Check if any adjacent tiles contain obstacles
     * @param dir The direction of movement
     * @param x Start x coordinate
     * @param y Start y coordinate
     * @param plane Start plane coordinate
     * @param width The width of area to check
     * @param height The height of area to check
     */
    abstract fun traversable(dir: Direction, x: Int, y: Int, plane: Int, width: Int, height: Int): Boolean

    /**
     * Check if any adjacent tiles contain obstacles
     * @param dir The direction of movement
     * @param position Start position
     * @param width The width of area to check
     * @param height The height of area to check
     */
    fun traversable(dir: Direction, position: Position, width: Int, height: Int): Boolean {
        return traversable(dir, position.x, position.y, position.plane, width, height)
    }

}