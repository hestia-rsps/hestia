package worlds.gregs.hestia.api.collision

import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.api.core.components.Position

abstract class ObjectCollision : PassiveSystem() {

    /**
     * Loads collision checks
     * @param position The entities position
     * @param single Whether to load a single tile or entire map
     */
    abstract fun load(position: Position, single: Boolean)

    /**
     * Checks whether there are any objects at [localX], [localY] that collide with [mask] type
     * Optimised for multiple checks in the same region after using [load]
     * @param localX The localX coordinate to check
     * @param localY The localY coordinate to check
     * @param mask The masks of object types to collide with
     * @return If there are any collisions
     */
    abstract fun collides(localX: Int, localY: Int, mask: Int): Boolean
}
