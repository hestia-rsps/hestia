package worlds.gregs.hestia.core.world.map.api

import net.mostlyoriginal.api.system.core.PassiveSystem

abstract class MapSettings : PassiveSystem() {
    /**
     * Loads the region tile height settings
     * @param mapContainerData The map file data
     * @return The decoded height settings
     */
    abstract fun load(mapContainerData: ByteArray): Array<Array<ByteArray>>

    /**
     * Adds masks to clipping based on the settings provided
     * @param regionX The regions x coordinate
     * @param regionY The regions y coordinate
     * @param settings The tile settings
     * @param rotation The chunks rotation
     * @param chunkX The chunks x coordinate
     * @param chunkY The chunks y coordinate
     * @param chunkPlane The chunks plane coordinate
     */
    abstract fun apply(regionX: Int, regionY: Int, settings: Array<Array<ByteArray>>, rotation: Int?, chunkX: Int?, chunkY: Int?, chunkPlane: Int?)
}