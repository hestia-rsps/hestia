package worlds.gregs.hestia.core.world.collision.api

import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.core.entity.entity.model.components.Position

abstract class ObjectCollision : PassiveSystem() {

    /**
     * Loads collision checks
     * @param position The entities position
     */
    abstract fun load(position: Position)

    /**
     * Checks whether there are any objects at [localX], [localY] that collide with [flag] type
     * Optimised for multiple checks in the same region after using [load]
     * @param localX The localX coordinate to check
     * @param localY The localY coordinate to check
     * @param flag The flags of object types to collide with
     * @return If there are any collisions
     */
    abstract fun collides(localX: Int, localY: Int, flag: Int): Boolean
}
