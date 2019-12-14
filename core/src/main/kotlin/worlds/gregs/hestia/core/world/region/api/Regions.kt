package worlds.gregs.hestia.core.world.region.api

import com.artemis.Aspect
import worlds.gregs.hestia.artemis.SubscriptionSystem

/**
 * Regions
 * Holds list of regions
 */
abstract class Regions(builder: Aspect.Builder) : SubscriptionSystem(builder) {

    /**
     * Returns the id of the region with [regionId]
     * @param regionId The region to find
     * @return The id of the region's entity
     */
    abstract fun getEntityId(regionId: Int): Int?

    /**
     * Checks if region [regionId] exists
     * @param regionId The region to find
     * @return Whether entity with [regionId] has been created
     */
    abstract fun contains(regionId: Int): Boolean

}