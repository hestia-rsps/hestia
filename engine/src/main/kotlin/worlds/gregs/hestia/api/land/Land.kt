package worlds.gregs.hestia.api.land

import net.mostlyoriginal.api.system.core.PassiveSystem


/**
 * Land
 * Area of objects
 */
abstract class Land : PassiveSystem() {

    /**
     * Clears objects for region [entityId]
     * @param entityId The regions entity id to clear
     */
    abstract fun unload(entityId: Int)

    /**
     * Adds an object to the land map
     * @param entityId The objects entity id
     * @param localX The local x coordinate of the object
     * @param localY The local y coordinate of the object
     * @param plane The local plane coordinate of the object
     * @param regionId The region to add the object
     */
    abstract fun addObject(entityId: Int, localX: Int, localY: Int, plane: Int, regionId: Int)

    /**
     * Removes an object from the land map
     * @param entityId The objects entity id
     * @param localX The local x coordinate of the object
     * @param localY The local y coordinate of the object
     * @param plane The local plane coordinate of the object
     * @param regionId The region that the object is in
     */
    abstract fun removeObject(entityId: Int, localX: Int, localY: Int, plane: Int, regionId: Int)

}