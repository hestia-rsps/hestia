package worlds.gregs.hestia.artemis.bag.map

/**
 * A bag of live entities with batched [insert] and [remove] actions when [sync] is called
 */
abstract class EntitySyncBag(private val maxEntitiesPerTick: Int) {

    /**
     * Total count of live entities
     */
    var size = 0
        private set

    /**
     * Map of entity id & client indices to be added <EntityId, ClientIndex>
     */
    private val insertions = LinkedHashMap<Int, Int>()
    /**
     * List of entity client indices to be removed <EntityId, ClientIndex>
     */
    private val removals = LinkedHashMap<Int, Int>()

    /**
     * Directly adds a live entity
     * @param entity The entity id to set
     * @param index The entities client index
     */
    open fun set(entity: Int, index: Int) {
        size++
    }

    /**
     * Directly removes a live entity
     * @param index The index of the entity to remove
     */
    open fun unset(index: Int) {
        size--
    }

    /**
     * Queues an entity to be inserted next [sync]
     * @param entity The entity id to add
     * @param index The entities client index
     */
    fun insert(entity: Int, index: Int) {
        insertions[entity] = index
    }

    /**
     * Queues an entity to be removed next [sync]
     * @param entity The entity id to remove
     * @param index The entities client index
     */
    fun remove(entity: Int, index: Int) {
        removals[entity] = index
    }

    /**
     * Checks if an entity is queued for insertion
     * @param entity The id of the entity to check
     * @return if entity is queued to be added
     */
    fun needsInsert(entity: Int): Boolean {
        return insertions.contains(entity)
    }

    /**
     * Checks if an entity is queued for removal
     * @param entity The id of the entity to check
     * @return if entity is queued to be removed
     */
    fun needsRemoval(entity: Int): Boolean {
        return removals.contains(entity)
    }

    /**
     * Checks if an entity will be inserted next sync
     * Inserted entities will be in the front [maxEntitiesPerTick]
     * @param entity The entity id to check
     * @return if the entity will be added next sync
     */
    fun canInsert(entity: Int): Boolean {
        var index = maxEntitiesPerTick.coerceAtMost(insertions.size)
        for (key in insertions.keys) {
            if (entity == key) {
                return true
            }
            if (--index == 0) {
                return false
            }
        }
        return false
    }

    /**
     * Checks if there is a live entity with [index]
     * @param index The client index to check
     * @return if entity exists
     */
    abstract fun containsIndex(index: Int): Boolean

    /**
     * Returns the entity id at [index]
     * @param index The client index to get
     * @return The entity id if exists
     */
    abstract fun getEntity(index: Int): Int

    /**
     * @return number of entities queued for removal
     */
    fun removals(): Int {
        return removals.size
    }

    /**
     * @return number of entities queued for insertion
     */
    fun insertions(): Int {
        return insertions.size
    }

    /**
     * Clears the bag
     */
    open fun clear() {
        size = 0
        insertions.clear()
        removals.clear()
    }

    /**
     * Performs a bag sync, adding up to [maxEntitiesPerTick] entities from [insertions]
     * and removing all entities listed in [removals]
     */
    fun sync(action: ((Int) -> Unit)? = null) {
        //Add a limited number of insertions
        val iterator = insertions.iterator()
        repeat(insertions.size.coerceAtMost(maxEntitiesPerTick)) {
            val next = iterator.next()
            //Additional processing
            action?.invoke(next.key)
            //Add the entity
            set(next.key, next.value)
            //Remove from [insertions] queue
            iterator.remove()
        }
        //Remove all entities in [removals]
        removals.values.forEach {
            unset(it)
        }
        removals.clear()
    }

    /**
     * Iterates live entities in inserted order
     */
    abstract fun forEach(action: (Int) -> Unit)

    /**
     * Iterates live entities in order of index
     */
    abstract fun forEachIndexed(action: (index: Int, Int?) -> Unit)

    override fun toString(): String {
        return "Inserts $insertions Removals $removals"
    }
}