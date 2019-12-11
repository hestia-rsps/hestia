package worlds.gregs.hestia.api.movement.types

import com.artemis.Aspect
import com.artemis.systems.IteratingSystem

/**
 * Move
 */
abstract class Move(builder: Aspect.Builder) : IteratingSystem(builder) {

    /**
     * @param entityId The entity to check
     * @return Whether the entity has move steps
     */
    abstract fun isMoving(entityId: Int): Boolean

    /**
     * @param entityId The entity to check
     * @return Whether the entity has a move step
     */
    abstract fun hasStep(entityId: Int): Boolean

}