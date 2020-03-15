package worlds.gregs.hestia.core.world.map.api

import com.artemis.Component

abstract class Collisions : Component() {

    /**
     * Returns collision flags for an entire plane
     * @param plane The plane of flags to return
     * @return All x & y collision flags
     */
    abstract fun getFlags(plane: Int): Array<IntArray>

    /**
     * Returns collision flags for a tile
     * @param localX The local x coordinate of the tile
     * @param localY The local y coordinate of the tile
     * @param plane The plane coordinate of the tile
     * @return collision flag value
     */
    abstract fun getFlag(localX: Int, localY: Int, plane: Int): Int

    /**
     * Adds a collision flag to tile at [localX], [localY], [plane]
     * @param localX The local x coordinate of the tile
     * @param localY The local y coordinate of the tile
     * @param plane The plane coordinate of the tile
     */
    abstract fun addFlag(localX: Int, localY: Int, plane: Int, value: Int)

    /**
     * Removes the collision flag of the tile at [localX], [localY], [plane]
     * @param localX The local x coordinate of the tile
     * @param localY The local y coordinate of the tile
     * @param plane The plane coordinate of the tile
     */
    abstract fun removeFlag(localX: Int, localY: Int, plane: Int, value: Int)

    /**
     * Sets the collision flag of tile at [localX], [localY], [plane]
     * @param localX The local x coordinate of the tile
     * @param localY The local y coordinate of the tile
     * @param plane The plane coordinate of the tile
     */
    abstract fun setFlag(localX: Int, localY: Int, plane: Int, value: Int)

}