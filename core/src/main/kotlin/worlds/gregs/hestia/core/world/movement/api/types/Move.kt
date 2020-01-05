package worlds.gregs.hestia.core.world.movement.api.types

import com.artemis.Aspect
import com.artemis.systems.IteratingSystem

/**
 * Move
 */
abstract class Move(builder: Aspect.Builder) : IteratingSystem(builder) {

    /**
     * @param entityId The entity to check
     * @return Whether the entity has a move step
     */
    abstract fun hasStep(entityId: Int): Boolean

}