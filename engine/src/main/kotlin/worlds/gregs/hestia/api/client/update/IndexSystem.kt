package worlds.gregs.hestia.api.client.update

import com.artemis.Component
import worlds.gregs.hestia.api.client.components.ClientIndex
import worlds.gregs.hestia.artemis.SubscriptionSystem
import worlds.gregs.hestia.services.Aspect
import kotlin.reflect.KClass

/**
 * Handles the unique index of an entity in a viewport
 */
abstract class IndexSystem <T : Component>(type: KClass<T>) : SubscriptionSystem(Aspect.all(ClientIndex::class, type)) {

    /**
     * Returns the entityId of entity using their index
     * @param index The client index to check
     * @return The entityId (if exists)
     */
    abstract fun getId(index: Int): Int?

}