package worlds.gregs.hestia.core.world.map.api

import net.mostlyoriginal.api.system.core.PassiveSystem

abstract class ClippingMasks : PassiveSystem() {
    /**
     * Get's a clipping mask for the tile at [localX], [localY], [plane]
     * @param entityId The region entity id
     * @param clipping The clipping to get the mask from
     * @param localX The local x coordinate of the wall
     * @param localY The local y coordinate of the wall
     * @param plane The plane coordinate of the wall
     */
    abstract tailrec fun getMask(entityId: Int, clipping: Clipping, localX: Int, localY: Int, plane: Int): Int

    /**
     * Adds clipping masks for an object
     * @param entityId The region entity id
     * @param localX The local x coordinate of the object
     * @param localY The local y coordinate of the object
     * @param plane The plane coordinate of the object
     * @param sizeX The width of the object
     * @param sizeY The height of the object
     * @param sky Whether the object blocks flying entities
     * @param sea Whether the object blocks swimming entities
     */
    abstract fun addObject(entityId: Int, localX: Int, localY: Int, plane: Int, sizeX: Short, sizeY: Short, sky: Boolean, sea: Boolean)

    /**
     * Removes clipping masks for an object
     * @param entityId The region entity id
     * @param localX The local x coordinate of the object
     * @param localY The local y coordinate of the object
     * @param plane The plane coordinate of the object
     * @param sizeX The width of the object
     * @param sizeY The height of the object
     * @param sky Whether the object blocks flying entities
     * @param sea Whether the object blocks swimming entities
     */
    abstract fun removeObject(entityId: Int, localX: Int, localY: Int, plane: Int, sizeX: Short, sizeY: Short, sky: Boolean, sea: Boolean)

    /**
     * Sets clipping masks for an object
     * @param entityId The region entity id
     * @param localX The local x coordinate of the object
     * @param localY The local y coordinate of the object
     * @param plane The plane coordinate of the object
     * @param sizeX The width of the object
     * @param sizeY The height of the object
     * @param sky Whether the object blocks flying entities
     * @param sea Whether the object blocks swimming entities
     */
    abstract fun setObject(entityId: Int, localX: Int, localY: Int, plane: Int, sizeX: Short, sizeY: Short, sky: Boolean, sea: Boolean)

    /**
     * Applies [changeType] changes to clipping mask for an object
     * @param entityId The region entity id
     * @param localX The local x coordinate of the object
     * @param localY The local y coordinate of the object
     * @param plane The plane coordinate of the object
     * @param sizeX The width of the object
     * @param sizeY The height of the object
     * @param sky Whether the object blocks flying entities
     * @param sea Whether the object blocks swimming entities
     * @param changeType How to change the clipping mask
     */
    abstract fun changeObject(entityId: Int, localX: Int, localY: Int, plane: Int, sizeX: Short, sizeY: Short, sky: Boolean, sea: Boolean, changeType: Int)

    /**
     * Adds clipping masks for a wall
     * @param entityId The region entity id
     * @param localX The local x coordinate of the wall
     * @param localY The local y coordinate of the wall
     * @param plane The plane coordinate of the wall
     * @param type The wall type
     * @param rotation The rotation of the wall
     * @param sky Whether the object blocks flying entities
     * @param sea Whether the object blocks swimming entities
     */
    abstract fun addWall(entityId: Int, localX: Int, localY: Int, plane: Int, type: Int, rotation: Int, sky: Boolean, sea: Boolean)

    /**
     * Removes clipping masks for a wall
     * @param entityId The region entity id
     * @param localX The local x coordinate of the wall
     * @param localY The local y coordinate of the wall
     * @param plane The plane coordinate of the wall
     * @param type The wall type
     * @param rotation The rotation of the wall
     * @param sky Whether the object blocks flying entities
     * @param sea Whether the object blocks swimming entities
     */
    abstract fun removeWall(entityId: Int, localX: Int, localY: Int, plane: Int, type: Int, rotation: Int, sky: Boolean, sea: Boolean)

    /**
     * Sets clipping masks for a wall
     * @param entityId The region entity id
     * @param localX The local x coordinate of the wall
     * @param localY The local y coordinate of the wall
     * @param plane The plane coordinate of the wall
     * @param type The wall type
     * @param rotation The rotation of the wall
     * @param sky Whether the object blocks flying entities
     * @param sea Whether the object blocks swimming entities
     */
    abstract fun setWall(entityId: Int, localX: Int, localY: Int, plane: Int, type: Int, rotation: Int, sky: Boolean, sea: Boolean)

    /**
     * Applies [changeType] changes to clipping mask for a wall
     * @param entityId The region entity id
     * @param localX The local x coordinate of the wall
     * @param localY The local y coordinate of the wall
     * @param plane The plane coordinate of the wall
     * @param type The wall type
     * @param rotation The rotation of the wall
     * @param sky Whether the object blocks flying entities
     * @param sea Whether the object blocks swimming entities
     * @param changeType How to change the clipping mask
     */
    abstract fun changeWall(entityId: Int, localX: Int, localY: Int, plane: Int, type: Int, rotation: Int, sky: Boolean, sea: Boolean, changeType: Int)

        /**
     * Adds clipping mask for a tile
     * @param entityId The region entity id
     * @param localX The x coordinate of the wall
     * @param localY The y coordinate of the wall
     * @param plane The plane coordinate of the wall
     * @param mask The mask to add
     */
    abstract fun addMask(entityId: Int, localX: Int, localY: Int, plane: Int, mask: Int)

    /**
     * Removes clipping mask for a tile
     * @param entityId The region entity id
     * @param localX The x coordinate of the wall
     * @param localY The y coordinate of the wall
     * @param plane The plane coordinate of the wall
     * @param mask The mask to remove
     */
    abstract fun removeMask(entityId: Int, localX: Int, localY: Int, plane: Int, mask: Int)

    /**
     * Sets clipping mask for a tile
     * @param entityId The region entity id
     * @param localX The x coordinate of the wall
     * @param localY The y coordinate of the wall
     * @param plane The plane coordinate of the wall
     * @param mask The mask to set
     */
    abstract fun setMask(entityId: Int, localX: Int, localY: Int, plane: Int, mask: Int)

    /**
     * Applies [changeType] changes to a tile's clipping mask
     * @param entityId The region entity id
     * @param localX The x coordinate of the wall
     * @param localY The y coordinate of the wall
     * @param plane The plane coordinate of the wall
     * @param mask The mask to apply
     * @param changeType How to change the clipping mask
     */
    abstract fun changeMask(entityId: Int, localX: Int, localY: Int, plane: Int, mask: Int, changeType: Int)
}