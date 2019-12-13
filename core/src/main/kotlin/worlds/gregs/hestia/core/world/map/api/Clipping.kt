package worlds.gregs.hestia.core.world.map.api

import com.artemis.Component

abstract class Clipping : Component() {

    /**
     * Returns clipping masks for an entire plane
     * @param plane The plane of masks to return
     * @return All x & y clipping masks
     */
    abstract fun getMasks(plane: Int): Array<IntArray>

    /**
     * Returns clipping masks for a tile
     * @param localX The local x coordinate of the tile
     * @param localY The local y coordinate of the tile
     * @param plane The plane coordinate of the tile
     * @return Clipping mask value
     */
    abstract fun getMask(localX: Int, localY: Int, plane: Int): Int

    /**
     * Adds a clipping mask to tile at [localX], [localY], [plane]
     * @param localX The local x coordinate of the tile
     * @param localY The local y coordinate of the tile
     * @param plane The plane coordinate of the tile
     */
    abstract fun addMask(localX: Int, localY: Int, plane: Int, value: Int)

    /**
     * Removes the clipping mask of the tile at [localX], [localY], [plane]
     * @param localX The local x coordinate of the tile
     * @param localY The local y coordinate of the tile
     * @param plane The plane coordinate of the tile
     */
    abstract fun removeMask(localX: Int, localY: Int, plane: Int, value: Int)

    /**
     * Sets the clipping mask of tile at [localX], [localY], [plane]
     * @param localX The local x coordinate of the tile
     * @param localY The local y coordinate of the tile
     * @param plane The plane coordinate of the tile
     */
    abstract fun setMask(localX: Int, localY: Int, plane: Int, value: Int)

}