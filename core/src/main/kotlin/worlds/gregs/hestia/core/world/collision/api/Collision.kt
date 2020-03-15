package worlds.gregs.hestia.core.world.collision.api

import net.mostlyoriginal.api.system.core.PassiveSystem

abstract class Collision : PassiveSystem() {

    /**
     * Loads ready for collision checks
     * @param entityId The entity to be checking for
     */
    abstract fun load(entityId: Int)

    /**
     * Checks whether any collisions at [x], [y]
     * @param x The x coordinate to check
     * @param y The y coordinate to check
     * @param flag The flags of object types to collide with
     * @return If there are any collisions
     */
    abstract fun collides(x: Int, y: Int, flag: Int): Boolean

}