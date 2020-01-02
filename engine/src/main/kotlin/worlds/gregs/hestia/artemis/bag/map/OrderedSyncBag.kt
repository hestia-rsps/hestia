package worlds.gregs.hestia.artemis.bag.map

/**
 * A [EntitySyncBag] of live entities ordered by index
 */
class OrderedSyncBag(capacity: Int, maxEntitiesPerTick: Int) : EntitySyncBag(maxEntitiesPerTick) {

    /**
     * Map of all active entities
     */
    private val entities = arrayOfNulls<Int?>(capacity)//Ideally replace with O(1) lookup sorted map

    override fun set(entity: Int, index: Int) {
        super.set(entity, index)
        entities[index] = entity
    }

    override fun unset(index: Int) {
        super.unset(index)
        entities[index] = null
    }

    override fun containsIndex(index: Int): Boolean {
        return entities[index] != null
    }

    override fun getEntity(index: Int): Int {
        return entities.getOrNull(index) ?: -1
    }

    override fun clear() {
        super.clear()
        entities.fill(null)
    }

    override fun forEach(action: (Int) -> Unit) = entities.filterNotNull().forEach(action)

    override fun forEachIndexed(action: (index: Int, Int?) -> Unit) = entities.forEachIndexed(action)

    override fun toString(): String {
        return "OrderedSyncBag $entities ${super.toString()}"
    }
}