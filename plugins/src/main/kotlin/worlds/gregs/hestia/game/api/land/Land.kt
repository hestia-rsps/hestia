package worlds.gregs.hestia.game.api.land

import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.game.map.GameObject


/**
 * Land
 * Area of objects
 */
abstract class Land : PassiveSystem() {

    /**
     * Clears objects for region [entityId]
     * @param entityId The entityId to clear
     */
    abstract fun unload(entityId: Int)

    abstract fun addObject(entityId: Int, `object`: GameObject, localX: Int, localY: Int, plane: Int)

    abstract fun removeObject(entityId: Int, `object`: GameObject, localX: Int, localY: Int)

}