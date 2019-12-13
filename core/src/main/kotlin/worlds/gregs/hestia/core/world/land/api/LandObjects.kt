package worlds.gregs.hestia.core.world.land.api

import net.mostlyoriginal.api.system.core.PassiveSystem


/**
 * LandObjects
 * Loads objects
 */
abstract class LandObjects : PassiveSystem() {

    /**
     * Loads all the objects within a region or chunk
     * @param entityId The regions entity id
     * @param x The regions x coordinate
     * @param y The regions y coordinate
     * @param landContainerData The cache map file data
     * @param settings The clipping settings
     * @param rotation The chunks rotation
     * @param chunkX The chunks x coordinate
     * @param chunkY The chunks x coordinate
     * @param chunkPlane The regions plane coordinate
     */
    abstract fun load(entityId: Int, x: Int, y: Int, landContainerData: ByteArray, settings: Array<Array<ByteArray>>?, rotation: Int? = null, chunkX: Int? = null, chunkY: Int? = null, chunkPlane: Int? = null)

}