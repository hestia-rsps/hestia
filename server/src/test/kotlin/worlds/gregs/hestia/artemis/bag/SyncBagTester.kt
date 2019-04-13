package worlds.gregs.hestia.artemis.bag

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal abstract class SyncBagTester {

    protected lateinit var bag: EntitySyncBag

    abstract fun bag(): EntitySyncBag

    @BeforeEach
    fun setup() {
        bag = bag()
    }

    @Test
    fun `Bag sets entity`() {
        //When
        set(10, 1)
        set(100, 3)
        //Then
        assertContains(1, true)
        assertContains(2, false)
        assertContains(3, true)
    }

    @Test
    fun `Bag un-sets entity`() {
        //Given
        set(10, 1)
        set(100, 3)
        //When
        unset(1)
        //Then
        assertContains(1, false)
        assertContains(2, false)
        assertContains(3, true)
    }

    @Test
    fun `Clear removes entities`() {
        //Given
        set(10, 1)
        set(100, 3)
        //When
        clear()
        //Then
        assertContains(1, false)
        assertContains(3, false)
    }

    internal fun unset(index: Int) {
        bag.unset(index)
    }

    internal fun set(value: Int, index: Int) {
        bag.set(value, index)
    }

    internal fun clear() {
        bag.clear()
    }

    internal fun assertContains(index: Int, expected: Boolean) {
        Assertions.assertThat(bag.containsIndex(index)).isEqualTo(expected)
    }
}