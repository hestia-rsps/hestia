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
     * Adds clipping masks to whole region or single chunk based on the settings provided
     * @param entityId The region entity
     * @param settings The map height settings
     * @param rotation Chunk rotation
     * @param chunkX Chunk x coordinate
     * @param chunkY Chunk y coordinate
     * @param chunkPlane Chunk plane coordinate
     */
    abstract fun apply(entityId: Int, settings: Array<Array<ByteArray>>, rotation: Int?, chunkX: Int?, chunkY: Int?, chunkPlane: Int?)
}