package worlds.gregs.hestia.core.world.map.api

import net.mostlyoriginal.api.system.core.PassiveSystem

abstract class MapCollisionFlags : PassiveSystem() {

    /**
     * Adds collision flags for an object
     * @param x The x coordinate of the object
     * @param y The y coordinate of the object
     * @param plane The plane coordinate of the object
     * @param sizeX The width of the object
     * @param sizeY The height of the object
     * @param sky Whether the object blocks flying entities
     * @param sea Whether the object blocks swimming entities
     */
    fun addObject(x: Int, y: Int, plane: Int, sizeX: Short, sizeY: Short, sky: Boolean, sea: Boolean) {
        changeObject(x, y, plane, sizeX, sizeY, sky, sea, ADD_FLAG)
    }

    /**
     * Removes collision flags for an object
     * @param x The x coordinate of the object
     * @param y The y coordinate of the object
     * @param plane The plane coordinate of the object
     * @param sizeX The width of the object
     * @param sizeY The height of the object
     * @param sky Whether the object blocks flying entities
     * @param sea Whether the object blocks swimming entities
     */
    fun removeObject(x: Int, y: Int, plane: Int, sizeX: Short, sizeY: Short, sky: Boolean, sea: Boolean) {
        changeObject(x, y, plane, sizeX, sizeY, sky, sea, REMOVE_FLAG)
    }

    /**
     * Sets collision flags for an object
     * @param x The x coordinate of the object
     * @param y The y coordinate of the object
     * @param plane The plane coordinate of the object
     * @param sizeX The width of the object
     * @param sizeY The height of the object
     * @param sky Whether the object blocks flying entities
     * @param sea Whether the object blocks swimming entities
     */
    fun setObject(x: Int, y: Int, plane: Int, sizeX: Short, sizeY: Short, sky: Boolean, sea: Boolean) {
        changeObject(x, y, plane, sizeX, sizeY, sky, sea, SET_FLAG)
    }

    /**
     * Applies [changeType] changes to collision flag for an object
     * @param x The x coordinate of the object
     * @param y The y coordinate of the object
     * @param plane The plane coordinate of the object
     * @param sizeX The width of the object
     * @param sizeY The height of the object
     * @param sky Whether the object blocks flying entities
     * @param sea Whether the object blocks swimming entities
     * @param changeType How to change the collision flag
     */
    abstract fun changeObject(x: Int, y: Int, plane: Int, sizeX: Short, sizeY: Short, sky: Boolean, sea: Boolean, changeType: Int)

    /**
     * Adds collision flags for a wall
     * @param x The x coordinate of the wall
     * @param y The y coordinate of the wall
     * @param plane The plane coordinate of the wall
     * @param type The wall type
     * @param rotation The rotation of the wall
     * @param sky Whether the object blocks flying entities
     * @param sea Whether the object blocks swimming entities
     */
    fun addWall(x: Int, y: Int, plane: Int, type: Int, rotation: Int, sky: Boolean, sea: Boolean) {
        changeWall(x, y, plane, type, rotation, sky, sea, ADD_FLAG)
    }

    /**
     * Removes collision flags for a wall
     * @param x The x coordinate of the wall
     * @param y The y coordinate of the wall
     * @param plane The plane coordinate of the wall
     * @param type The wall type
     * @param rotation The rotation of the wall
     * @param sky Whether the object blocks flying entities
     * @param sea Whether the object blocks swimming entities
     */
    fun removeWall(x: Int, y: Int, plane: Int, type: Int, rotation: Int, sky: Boolean, sea: Boolean) {
        changeWall(x, y, plane, type, rotation, sky, sea, REMOVE_FLAG)
    }

    /**
     * Sets collision flags for a wall
     * @param x The x coordinate of the wall
     * @param y The y coordinate of the wall
     * @param plane The plane coordinate of the wall
     * @param type The wall type
     * @param rotation The rotation of the wall
     * @param sky Whether the object blocks flying entities
     * @param sea Whether the object blocks swimming entities
     */
    fun setWall(x: Int, y: Int, plane: Int, type: Int, rotation: Int, sky: Boolean, sea: Boolean) {
        changeWall(x, y, plane, type, rotation, sky, sea, SET_FLAG)
    }

    /**
     * Applies [changeType] changes to collision flag for a wall
     * @param x The x coordinate of the wall
     * @param y The y coordinate of the wall
     * @param plane The plane coordinate of the wall
     * @param type The wall type
     * @param rotation The rotation of the wall
     * @param sky Whether the object blocks flying entities
     * @param sea Whether the object blocks swimming entities
     * @param changeType How to change the collision flag
     */
    abstract fun changeWall(x: Int, y: Int, plane: Int, type: Int, rotation: Int, sky: Boolean, sea: Boolean, changeType: Int)

    /**
     * Adds collision flag for a tile
     * @param x The x coordinate of the wall
     * @param y The y coordinate of the wall
     * @param plane The plane coordinate of the wall
     * @param flag The flag to add
     */
    fun addFlag(x: Int, y: Int, plane: Int, flag: Int) {
        changeFlag(x, y, plane, flag, ADD_FLAG)
    }

    /**
     * Removes collision flag for a tile
     * @param x The x coordinate of the wall
     * @param y The y coordinate of the wall
     * @param plane The plane coordinate of the wall
     * @param flag The flag to remove
     */
    fun removeFlag(x: Int, y: Int, plane: Int, flag: Int) {
        changeFlag(x, y, plane, flag, REMOVE_FLAG)
    }

    /**
     * Sets collision flag for a tile
     * @param x The x coordinate of the wall
     * @param y The y coordinate of the wall
     * @param plane The plane coordinate of the wall
     * @param flag The flag to set
     */
    fun setFlag(x: Int, y: Int, plane: Int, flag: Int) {
        changeFlag(x, y, plane, flag, SET_FLAG)
    }

    /**
     * Applies [changeType] changes to a tile's collision flag
     * @param x The x coordinate of the wall
     * @param y The y coordinate of the wall
     * @param plane The plane coordinate of the wall
     * @param flag The flag to apply
     * @param changeType How to change the collision flag
     */
    abstract fun changeFlag(x: Int, y: Int, plane: Int, flag: Int, changeType: Int)

    companion object {
        const val ADD_FLAG = 0
        const val REMOVE_FLAG = 1
        const val SET_FLAG = 2
    }
}