package worlds.gregs.hestia.api.update

import com.artemis.Aspect
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.game.update.DisplayFlag

abstract class Sync(builder: Aspect.Builder) : IteratingSystem(builder) {

    /**
     * Skip the processing and write an empty packet
     * @param locals List of local entity ids
     * @param globals List of global entity ids
     */
    abstract fun skip(locals: List<Int>, globals: List<Int>)

    /**
     * Process a single local entity
     * @param entityId client entity
     * @param local local entity
     * @param type [DisplayFlag]
     * @param update if requires an update
     */
    abstract fun local(entityId: Int, local: Int, type: DisplayFlag?, update: Boolean)

    /**
     * Process a single global entity
     * @param entityId client entity
     * @param global global entity
     * @param type [DisplayFlag]
     * @param update if requires an update
     */
    abstract fun global(entityId: Int, global: Int, type: DisplayFlag?, update: Boolean)

}