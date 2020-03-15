package worlds.gregs.hestia.core.world.map.api

import net.mostlyoriginal.api.system.core.PassiveSystem

/**
 * Map
 * Area of collision data
 */
abstract class Map : PassiveSystem() {

    /**
     * Clears map collision data for region [entityId]
     * @param entityId The entityId to clear
     */
    abstract fun unload(entityId: Int)

    /**
     * Get's a region's collision map
     * @param entityId The entityId of the region
     * @return The collision map
     */
    abstract fun getCollision(entityId: Int?): Collisions?

    /**
     * Creates or returns existing region collision map
     * @param entityId The entityId of the region
     * @return The collision map
     */
    abstract fun createCollision(entityId: Int): Collisions
}