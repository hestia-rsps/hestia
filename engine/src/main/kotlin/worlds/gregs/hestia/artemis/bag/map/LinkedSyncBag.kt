package worlds.gregs.hestia.artemis.bag.map

/**
 * A [EntitySyncBag] of live entities with a predictable iteration order
 */
class LinkedSyncBag(maxEntitiesPerTick: Int) : EntitySyncBag(maxEntitiesPerTick) {

    /**
     * Map of all active entities <ClientIndex, EntityId>
     */
    private val entities = LinkedHashMap<Int, Int>()

    override fun set(entity: Int, index: Int) {
        super.set(entity, index)
        entities[index] = entity
    }

    override fun unset(index: Int) {
        super.unset(index)
        entities.remove(index)
    }

    override fun containsIndex(index: Int): Boolean {
        return entities[index] != null
    }

    override fun getEntity(index: Int): Int {
        return entities.getOrDefault(index, -1)
    }

    override fun clear() {
        super.clear()
        entities.clear()
    }

    override fun forEach(action: (Int) -> Unit) = entities.values.forEach(action)

    override fun forEachIndexed(action: (index: Int, Int?) -> Unit) = entities.forEach(action)

    override fun toString(): String {
        return "LinkedSyncBag $entities ${super.toString()}"
    }
}