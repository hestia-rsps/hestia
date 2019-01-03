package worlds.gregs.hestia.game.plugins.region.systems.load

import net.mostlyoriginal.api.system.core.PassiveSystem

/**
 * ChunkRotationSystem
 * Rotation algorithms used by [MapSettingsSystem] & [MapObjectSystem]
 */
class ChunkRotationSystem : PassiveSystem() {

    /**
     * Get's the X coordinate of a mask or object which have been rotated [mapRotation]
     * @param x Original x position
     * @param y Original y position
     * @param mapRotation The rotation of the map chunk
     * @param sizeX Optional width if rotating an object
     * @param sizeY Optional height if rotating an object
     * @param objectRotation Optional face direction of an object
     */
    fun translateX(x: Int, y: Int, mapRotation: Int, sizeX: Short? = null, sizeY: Short? = null, objectRotation: Int? = null): Int {
        return translate(x, y, mapRotation, sizeX, sizeY, objectRotation, 2..3, 2)
    }

    /**
     * Get's the Y coordinate of a mask or object which have been rotated [mapRotation]
     * @param x Original x position
     * @param y Original y position
     * @param mapRotation The rotation of the map chunk
     * @param sizeX Optional width if rotating an object
     * @param sizeY Optional height if rotating an object
     * @param objectRotation Optional face direction of an object
     */
    fun translateY(x: Int, y: Int, mapRotation: Int, sizeX: Short? = null, sizeY: Short? = null, objectRotation: Int? = null): Int {
        return translate(x, y, mapRotation, sizeX, sizeY, objectRotation, 1..2, 1)
    }

    private fun translate(x: Int, y: Int, mapRotation: Int, sizeX: Short?, sizeY: Short?, objectRotation: Int?, range: IntRange, rotation: Int): Int {
        //Flip the position depending on if we're translating x or y and the rotation in use
        val position = if (mapRotation and 0x1 == (2 - rotation)) x else y
        //If the map rotation is in the range (specified by whether x or y rotating)
        return when (mapRotation) {
            in range -> {
                /*
                   Calculation is: 7 - position - size
                   Size only if we're rotating an object, for masks use 0

                   Flip sizes if mapRotation equals the rotation (depending on rotating x or y again)
                   Flip again if the objects rotation is factor of 1
                 */
                7 - position - if(sizeX != null && sizeY != null && objectRotation != null) {
                    (if (mapRotation == rotation != (objectRotation and 0x1 == 1)) sizeX else sizeY) - 1//TODO this can be simplified, not sure how yet
                } else {
                    0
                }
            }
            //Otherwise just return the potentially flipped position
            else -> position
        }
    }
}