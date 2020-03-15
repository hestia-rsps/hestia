package worlds.gregs.hestia.core.world.collision.api

import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.core.entity.entity.model.components.Position

abstract class EntityCollision : PassiveSystem() {

    /**
     * Loads for collision checks
     * @param entityId The entity loading
     * @param position The position of the entity
     */
    abstract fun load(entityId: Int, position: Position)

    /**
     * Checks whether there are any collisions at [x], [y]
     * @param x The x coordinate to check
     * @param y The y coordinate to check
     * @return Whether there is an entity to collide with
     */
    abstract fun collides(x: Int, y: Int, flag: Int): Boolean

}
