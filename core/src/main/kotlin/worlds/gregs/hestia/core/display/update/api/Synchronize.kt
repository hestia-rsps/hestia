package worlds.gregs.hestia.core.display.update.api

import world.gregs.hestia.core.network.codec.message.Message
import worlds.gregs.hestia.artemis.ParallelSystem
import worlds.gregs.hestia.artemis.bag.EntitySyncBag
import worlds.gregs.hestia.core.display.client.model.components.NetworkSession
import worlds.gregs.hestia.core.display.client.model.components.Viewport
import worlds.gregs.hestia.network.update.sync.Update
import worlds.gregs.hestia.service.Aspect

abstract class Synchronize<T : Message> : ParallelSystem(Aspect.all(NetworkSession::class, Viewport::class)) {

    /**
     * Checks if an entity has any blocks that need updating
     * @param entity The id of the entity to check
     */
    abstract fun sync(entity: Int): T

    /**
     * Checks if an entity has any blocks that need updating
     * @param entity The id of the entity to check
     */
    abstract fun hasUpdateBlocks(entity: Int): Boolean

    /**
     * Checks whether a local player needs a sync update
     * @param entity The main entity
     * @param bag The main entities viewport entity list
     * @param local The local entity to check for a sync update
     * @return The sync factory (if necessary)
     */
    abstract fun getLocalSync(entity: Int, bag: EntitySyncBag, local: Int): SyncFactory<*>?

    /**
     * Checks whether a global player needs a sync update
     * @param entity The main entity
     * @param bag The main entities viewport entity list
     * @param global The global entity to check for a sync update
     * @return The sync factory (if necessary)
     */
    abstract fun getGlobalSync(entity: Int, bag: EntitySyncBag, global: Int?): SyncFactory<*>?
    /**
     * Adds the entities update blocks to [update]
     * @param update The update to add blocks too
     * @param entity The entity who's looking
     * @param local The entity who's updates to add
     * @param added Whether the local entity has just been added
     */
    abstract fun updateBlocks(update: Update, entity: Int, local: Int, added: Boolean)

    companion object {
        const val MAX_ENTITIES_PER_TICK = 40
    }
}