package worlds.gregs.hestia.game.api.map

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
     * Get's a region's projectile clipping map
     * @param entityId The entityId of the region
     * @return The clipping map
     */
    abstract fun getProjectileMap(entityId: Int?): Clipping?

    /**
     * Creates or returns existing region clipping map
     * @param entityId The entityId of the region
     * @return The clipping map
     */
    abstract fun createClipping(entityId: Int): Clipping

    /**
     * Creates or returns existing region projectile clipping map
     * @param entityId The entityId of the region
     * @return The clipping map
     */
    abstract fun createProjectileMap(entityId: Int): Clipping
}