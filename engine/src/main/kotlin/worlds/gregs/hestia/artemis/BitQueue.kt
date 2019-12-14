package worlds.gregs.hestia.artemis

import com.artemis.utils.BitVector
import java.util.*

/**
 * An integer [LinkedList] with a [BitVector] for optimised [contains] lookup
 * at the cost of memory & io speeds
 */
class BitQueue : LinkedList<Int>() {

    private val vector = BitVector()

    override fun add(element: Int): Boolean {
        vector.set(element)
        return super.add(element)
    }

    override fun remove(element: Int): Boolean {
        vector.unsafeClear(element)
        return super.remove(element)
    }

    override fun poll(): Int? {
        val head = super.poll()
        if(head != null) {
            vector.unsafeClear(head)
        }
        return head
    }

    override fun contains(element: Int): Boolean {
        return vector.get(element)
    }
}