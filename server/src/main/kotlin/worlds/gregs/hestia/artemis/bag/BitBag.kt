package worlds.gregs.hestia.artemis.bag

import com.artemis.utils.BitVector
import com.artemis.utils.IntBag

/**
 * Combines storage of [IntBag] with lookup speed of [BitVector] and delayed addition and removal
 * Increased memory use & insert/remove speeds but faster [contains] searches
 */
open class BitBag(capacity: Int = 64) : IntBag(capacity), MutableIterable<Int> {

    private val vector = BitVector()

    override fun add(value: Int) {
        vector.set(value)
        super.add(value)
    }

    /**
     * Performance increase of expanding storage only once
     */
    open fun addAll(vararg entities: Int) {
        val max = entities.max() ?: 0
        val size = size() + entities.size
        ensureCapacity(size)
        vector.ensureCapacity(max)
        entities.forEach {
            add(it)
        }
    }

    override fun remove(index: Int): Int {
        val removed = super.remove(index)
        vector.unsafeClear(removed)
        return removed
    }

    override fun removeValue(value: Int): Boolean {
        vector.clear(value)
        return super.removeValue(value)
    }

    override fun contains(value: Int): Boolean {
        return vector.get(value)
    }

    override fun clear() {
        vector.clear()
        super.clear()
    }

    override fun iterator(): MutableIterator<Int> {
        return BitBagIterator(this)
    }

    internal class BitBagIterator(private val bag: BitBag) : MutableIterator<Int> {
        private var cursor = 0
        private var last = -1

        override fun hasNext(): Boolean {
            return cursor < bag.size
        }

        override fun next(): Int {
            val i = cursor
            if(i >= bag.size) {
                throw NoSuchElementException()
            }
            if(i >= bag.data.size) {
                throw ConcurrentModificationException()
            }
            last = i
            return bag.data[cursor++]
        }

        override fun remove() {
            if(last < 0) {
                throw IllegalStateException()
            }

            try {
                bag.remove(last)
                cursor = last
                last = -1
            } catch (e: IndexOutOfBoundsException) {
                throw ConcurrentModificationException()
            }
        }

    }
}