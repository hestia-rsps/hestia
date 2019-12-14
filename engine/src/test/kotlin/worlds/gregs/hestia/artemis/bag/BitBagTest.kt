package worlds.gregs.hestia.artemis.bag

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.artemis.toArray

internal class BitBagTest {

    private lateinit var bag: BitBag
    private lateinit var iterator: MutableIterator<Int>

    @BeforeEach
    fun setup() {
        bag = BitBag()
    }

    @Test
    fun `AddAll correctly adds all`() {
        //When
        bag.addAll(10, 100, 1000)
        //Then
        assertBag(10, 100, 1000)
    }

    @Test
    fun `Add contains`() {
        //Given
        add(10)
        //When
        add(100)
        //Then
        assertContains(100, true)
        assertBag(10, 100)
    }

    @Test
    fun `Remove doesn't contain`() {
        //Given
        add(10, 100, 1000)
        //When
        remove(100)
        //Then
        assertContains(100, false)
        assertBag(10, 1000)
    }

    @Test
    fun `iterates all`() {
        //Given
        add(10, 100, 1000)
        //When
        iterate()
        //Then
        assertHasNext(true)
        assertNext(10)
        assertHasNext(true)
        assertNext(100)
        assertHasNext(true)
        assertNext(1000)
        assertHasNext(false)
    }

    @Test
    fun `iterate removes`() {
        //Given
        add(10, 100, 1000)
        //When
        iterate()
        //Then
        assertHasNext(true)
        assertNext(10)
        assertHasNext(true)
        assertNext(100)
        iteratorRemove()
        assertHasNext(true)
        assertNext(1000)
        assertHasNext(false)
        assertBag(10, 1000)
    }

    @Test
    fun `iterate correctly handles mutations`() {
        //Given
        add(10, 100, 1000)
        //When
        iterate()
        //Then
        assertHasNext(true)
        assertNext(10)
        assertHasNext(true)
        assertNext(100)

        iteratorRemove()
        add(10000)

        assertHasNext(true)
        assertNext(1000)
        assertHasNext(true)
        assertNext(10000)
        assertHasNext(false)
        assertBag(10, 1000, 10000)
    }

    private fun iterate() {
        iterator = bag.iterator()
    }

    private fun iteratorRemove() {
        iterator.remove()
    }

    private fun add(vararg values: Int) {
        values.forEach {
            bag.add(it)
        }
    }

    private fun remove(value: Int) {
        bag.removeValue(value)
    }

    private fun assertHasNext(has: Boolean) {
        assertThat(iterator.hasNext()).isEqualTo(has)
    }

    private fun assertNext(expected: Int) {
        assertThat(iterator.next()).isEqualTo(expected)
    }

    private fun assertContains(value: Int, expected: Boolean) {
        assertThat(bag.contains(value)).isEqualTo(expected)
    }

    private fun assertBag(vararg values: Int) {
        Assertions.assertArrayEquals(values, bag.toArray())
    }
}