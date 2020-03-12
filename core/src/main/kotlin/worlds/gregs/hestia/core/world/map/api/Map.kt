package worlds.gregs.hestia.core.world.map.api

import net.mostlyoriginal.api.system.core.PassiveSystem

/**
 * Map
 * Area of clipping data
 */
abstract class Map : PassiveSystem() {

    /**
     * Clears map clipping data for region [entityId]
     * @param entityId The entityId to clear
     */
    abstract fun unload(entityId: Int)

    /**
     * Get's a region's clipping map
     * @param entityId The entityId of the region
     * @return The clipping map
     */
    abstract fun getClipping(entityId: Int?): Clipping?

    /**
     * Creates or returns existing region clipping map
     * @param entityId The entityId of the region
     * @return The clipping map
     */
    abstract fun createClipping(entityId: Int): Clipping
}