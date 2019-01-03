package worlds.gregs.hestia.api.movement.types

import com.artemis.Aspect
import com.artemis.systems.IteratingSystem

/**
 * Run
 */
abstract class Run(builder: Aspect.Builder) : IteratingSystem(builder) {

    /**
     * @param entityId The entity to check
     * @return Whether the entity has run toggled
     */
    abstract fun isRunning(entityId: Int): Boolean

    /**
     * @param entityId The entity to check
     * @return Whether the entity has a run step
     */
    abstract fun hasStep(entityId: Int): Boolean

}