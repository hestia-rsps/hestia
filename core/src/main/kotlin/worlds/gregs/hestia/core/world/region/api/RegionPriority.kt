package worlds.gregs.hestia.core.world.region.api

import net.mostlyoriginal.api.system.core.PassiveSystem

/**
 * RegionPriority
 * Keeps count of prioritised entities (players)
 */
abstract class RegionPriority : PassiveSystem() {

    /**
     * Adds priority to the region [regionId]
     * @param regionId The region to increase priority
     */
    abstract fun add(regionId: Int)

    /**
     * Removes priority from the region [regionId]
     * @param regionId The region to decrease priority
     */
    abstract fun remove(regionId: Int)

}