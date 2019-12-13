package worlds.gregs.hestia.core.world.collision.api

import net.mostlyoriginal.api.system.core.PassiveSystem

abstract class Collision : PassiveSystem() {

    /**
     * Loads ready for collision checks
     * @param entityId The entity to be checking for
     * @param step Whether incoming data is from a step or a path calculation
     */
    abstract fun load(entityId: Int, step: Boolean, x: Int? = null, y: Int? = null)

    /**
     * Checks whether any collisions at [x], [y]
     * @param x The x coordinate to check
     * @param y The y coordinate to check
     * @param mask The masks of object types to collide with
     * @return If there are any collisions
     */
    abstract fun collides(x: Int, y: Int, mask: Int): Boolean

}