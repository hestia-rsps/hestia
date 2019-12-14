package worlds.gregs.hestia.core.entity.item.floor.api

import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.artemis.SubscriptionSystem
import worlds.gregs.hestia.artemis.all
import worlds.gregs.hestia.artemis.exclude
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.components.Type
import worlds.gregs.hestia.core.entity.item.floor.model.components.*

abstract class FloorItems : SubscriptionSystem(Aspect.one(Private::class, Public::class).all(Amount::class, Type::class, Position::class).exclude(SpawnPoint::class)) {

    /**
     * Sends [entityId] to all local entities with a [ReloadFloorItems] and [Position]
     * @param entityId The item entity id to send
     */
    abstract fun sendFloorItem(entityId: Int)

    /**
     * Updates [entityId] amount to all local entities with a [ReloadFloorItems] and [Position]
     * @param entityId The item entity id to send
     * @param quantity The new quantity to display
     */
    abstract fun updateFloorItem(entityId: Int, quantity: Int)

    /**
     * Removes [entityId] from all local entities with a [ReloadFloorItems] and [Position]
     * @param entityId The item entity id to remove
     */
    abstract fun removeFloorItem(entityId: Int)

    /**
     * Sends/Updates all floor items in a [chunk] for one [playerId]
     * @param playerId The entity of the player to send the update too
     * @param chunk The id of the chunk to update
     * @param clear Whether to clear old floor items
     */
    abstract fun sendFloorItems(playerId: Int, chunk: Int, clear: Boolean)

    /**
     * Removes all floor items in a [chunk] for one [playerId]
     * @param playerId The entity of the player to send the update too
     * @param chunk The id of the chunk to update
     */
    abstract fun clearFloorItems(playerId: Int, chunk: Int)

    /**
     * Checks if a chunk has items
     * @param chunk The id of the chunk to check
     */
    abstract fun hasItems(chunk: Int): Boolean

    /**
     * Returns items in a chunk
     * @param chunk The id of the chunk to check
     * @return List of floor item entity ids
     */
    abstract fun getItems(chunk: Int): List<Int>

    /**
     * Returns all items
     * @return List of floor item entity ids
     */
    abstract fun getItems(): List<Int>

}