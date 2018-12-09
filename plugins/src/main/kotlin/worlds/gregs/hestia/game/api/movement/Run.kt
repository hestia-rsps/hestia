package worlds.gregs.hestia.game.api.movement

import com.artemis.Aspect
import com.artemis.systems.IteratingSystem

/**
 * Run
 */
abstract class Run(builder: Aspect.Builder) : IteratingSystem(builder) {

    /**
     * @param entityId The entity to check
     * @return Whether the entity has run steps
     */
    abstract fun isRunning(entityId: Int): Boolean

    /**
     * @param entityId The entity to check
     * @return Whether the entity has a run step
     */
    abstract fun hasStep(entityId: Int): Boolean

}