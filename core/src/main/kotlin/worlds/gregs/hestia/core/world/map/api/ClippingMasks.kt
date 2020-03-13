package worlds.gregs.hestia.core.world.map.api

import net.mostlyoriginal.api.system.core.PassiveSystem

abstract class ClippingMasks : PassiveSystem() {

    /**
     * Adds clipping masks for an object
     * @param x The x coordinate of the object
     * @param y The y coordinate of the object
     * @param plane The plane coordinate of the object
     * @param sizeX The width of the object
     * @param sizeY The height of the object
     * @param sky Whether the object blocks flying entities
     * @param sea Whether the object blocks swimming entities
     */
    fun addObject(x: Int, y: Int, plane: Int, sizeX: Short, sizeY: Short, sky: Boolean, sea: Boolean) {
        changeObject(x, y, plane, sizeX, sizeY, sky, sea, ADD_MASK)
    }

    /**
     * Removes clipping masks for an object
     * @param x The x coordinate of the object
     * @param y The y coordinate of the object
     * @param plane The plane coordinate of the object
     * @param sizeX The width of the object
     * @param sizeY The height of the object
     * @param sky Whether the object blocks flying entities
     * @param sea Whether the object blocks swimming entities
     */
    fun removeObject(x: Int, y: Int, plane: Int, sizeX: Short, sizeY: Short, sky: Boolean, sea: Boolean) {
        changeObject(x, y, plane, sizeX, sizeY, sky, sea, REMOVE_MASK)
    }

    /**
     * Sets clipping masks for an object
     * @param x The x coordinate of the object
     * @param y The y coordinate of the object
     * @param plane The plane coordinate of the object
     * @param sizeX The width of the object
     * @param sizeY The height of the object
     * @param sky Whether the object blocks flying entities
     * @param sea Whether the object blocks swimming entities
     */
    fun setObject(x: Int, y: Int, plane: Int, sizeX: Short, sizeY: Short, sky: Boolean, sea: Boolean) {
        changeObject(x, y, plane, sizeX, sizeY, sky, sea, SET_MASK)
    }

    /**
     * Applies [changeType] changes to clipping mask for an object
     * @param x The x coordinate of the object
     * @param y The y coordinate of the object
     * @param plane The plane coordinate of the object
     * @param sizeX The width of the object
     * @param sizeY The height of the object
     * @param sky Whether the object blocks flying entities
     * @param sea Whether the object blocks swimming entities
     * @param changeType How to change the clipping mask
     */
    abstract fun changeObject(x: Int, y: Int, plane: Int, sizeX: Short, sizeY: Short, sky: Boolean, sea: Boolean, changeType: Int)

    /**
     * Adds clipping masks for a wall
     * @param x The x coordinate of the wall
     * @param y The y coordinate of the wall
     * @param plane The plane coordinate of the wall
     * @param type The wall type
     * @param rotation The rotation of the wall
     * @param sky Whether the object blocks flying entities
     * @param sea Whether the object blocks swimming entities
     */
    fun addWall(x: Int, y: Int, plane: Int, type: Int, rotation: Int, sky: Boolean, sea: Boolean) {
        changeWall(x, y, plane, type, rotation, sky, sea, ADD_MASK)
    }

    /**
     * Removes clipping masks for a wall
     * @param x The x coordinate of the wall
     * @param y The y coordinate of the wall
     * @param plane The plane coordinate of the wall
     * @param type The wall type
     * @param rotation The rotation of the wall
     * @param sky Whether the object blocks flying entities
     * @param sea Whether the object blocks swimming entities
     */
    fun removeWall(x: Int, y: Int, plane: Int, type: Int, rotation: Int, sky: Boolean, sea: Boolean) {
        changeWall(x, y, plane, type, rotation, sky, sea, REMOVE_MASK)
    }

    /**
     * Sets clipping masks for a wall
     * @param x The x coordinate of the wall
     * @param y The y coordinate of the wall
     * @param plane The plane coordinate of the wall
     * @param type The wall type
     * @param rotation The rotation of the wall
     * @param sky Whether the object blocks flying entities
     * @param sea Whether the object blocks swimming entities
     */
    fun setWall(x: Int, y: Int, plane: Int, type: Int, rotation: Int, sky: Boolean, sea: Boolean) {
        changeWall(x, y, plane, type, rotation, sky, sea, SET_MASK)
    }

    /**
     * Applies [changeType] changes to clipping mask for a wall
     * @param x The x coordinate of the wall
     * @param y The y coordinate of the wall
     * @param plane The plane coordinate of the wall
     * @param type The wall type
     * @param rotation The rotation of the wall
     * @param sky Whether the object blocks flying entities
     * @param sea Whether the object blocks swimming entities
     * @param changeType How to change the clipping mask
     */
    abstract fun changeWall(x: Int, y: Int, plane: Int, type: Int, rotation: Int, sky: Boolean, sea: Boolean, changeType: Int)

    /**
     * Adds clipping mask for a tile
     * @param x The x coordinate of the wall
     * @param y The y coordinate of the wall
     * @param plane The plane coordinate of the wall
     * @param mask The mask to add
     */
    fun addMask(x: Int, y: Int, plane: Int, mask: Int) {
        changeMask(x, y, plane, mask, ADD_MASK)
    }

    /**
     * Removes clipping mask for a tile
     * @param x The x coordinate of the wall
     * @param y The y coordinate of the wall
     * @param plane The plane coordinate of the wall
     * @param mask The mask to remove
     */
    fun removeMask(x: Int, y: Int, plane: Int, mask: Int) {
        changeMask(x, y, plane, mask, REMOVE_MASK)
    }

    /**
     * Sets clipping mask for a tile
     * @param x The x coordinate of the wall
     * @param y The y coordinate of the wall
     * @param plane The plane coordinate of the wall
     * @param mask The mask to set
     */
    fun setMask(x: Int, y: Int, plane: Int, mask: Int) {
        changeMask(x, y, plane, mask, SET_MASK)
    }

    /**
     * Applies [changeType] changes to a tile's clipping mask
     * @param x The x coordinate of the wall
     * @param y The y coordinate of the wall
     * @param plane The plane coordinate of the wall
     * @param mask The mask to apply
     * @param changeType How to change the clipping mask
     */
    abstract fun changeMask(x: Int, y: Int, plane: Int, mask: Int, changeType: Int)

    companion object {
        const val ADD_MASK = 0
        const val REMOVE_MASK = 1
        const val SET_MASK = 2
    }
}