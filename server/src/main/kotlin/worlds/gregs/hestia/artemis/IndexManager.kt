package worlds.gregs.hestia.artemis

import java.util.*

/**
 * Provide unique indices for T.
 * Based on @see [net.mostlyoriginal.api.utils.IndexManager].
 */
internal class IndexManager<T> {

    /** Amount of EntitySystem indices.  */
    private var INDEX = 0

    private val indices = IdentityHashMap<T, Int>()

    fun getIndexFor(es: T): Int {
        var index: Int? = indices[es]
        if (index == null) {
            index = INDEX++
            indices[es] = index
        }
        return index
    }
}