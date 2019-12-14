package worlds.gregs.hestia.artemis.bag

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.artemis.bag.map.EntitySyncBag

internal class EntitySyncBagTest {

    private lateinit var bag: EntitySyncBag

    @BeforeEach
    fun setup() {
        bag = object: EntitySyncBag(40) {
            override fun containsIndex(index: Int): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun getEntity(index: Int): Int {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun forEach(action: (Int) -> Unit) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun forEachIndexed(action: (index: Int, Int?) -> Unit) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
    }

    @Test
    fun `Bag doesn't contain insertion before sync`() {
        //Given
        insert(10, 1)
        //Then
        assertSize(0)
        assertInserted(10, true)
    }

    @Test
    fun `Bag contains insertion after sync`() {
        //Given
        insert(10, 1)
        //When
        sync()
        //Then
        assertSize(1)
        assertInserted(10, false)
    }

    @Test
    fun `Bag contains removal before sync`() {
        //Given
        set(100, 2)
        //When
        remove(100, 2)
        //Then
        assertSize(1)
        assertRemoval(100, true)
    }

    @Test
    fun `Remove doesn't contain removal after sync`() {
        //Given
        set(1000, 3)
        //When
        remove(1000, 3)
        sync()
        //Then
        assertSize(0)
        assertRemoval(1000, false)
    }

    @Test
    fun `Insert and remove both registered before sync`() {
        //Given
        insert(100, 2)
        remove(100, 2)
        //Then
        assertSize(0)
        assertInserted(100, true)
        assertRemoval(100, true)
    }

    @Test
    fun `Bag doesn't contains insert or removal after sync`() {
        //Given
        insert(100, 2)
        remove(100, 2)
        //When
        sync()
        //Then
        assertSize(0)
        assertInserted(100, false)
        assertRemoval(100, false)
    }

    @Test
    fun `Bag queues large insertion counts`() {
        //Given
        repeat(50) {
            insert(it, it + 1)
        }
        //Then
        repeat(50) {
            assertInserted(it, true)
        }
    }

    @Test
    fun `Bag queues large removal counts`() {
        //Given
        repeat(50) {
            set(it, it + 1)
            remove(it, it + 1)
        }
        //Then
        repeat(50) {
            assertRemoval(it, true)
        }
    }

    @Test
    fun `Bag insertions limited per sync`() {
        val limit = 40
        //Given
        repeat(limit + 10) {
            insert(it, it + 1)
        }
        //When
        sync()
        //Then
        assertSize(limit)
        repeat(limit) {
            assertInserted(it, false)
        }
        for(it in limit + 1 until limit + 10) {
            assertInserted(it, true)
        }
    }

    @Test
    fun `Bag removals have no limit per sync`() {
        val count = 50
        //Given
        repeat(count) {
            set(it, it + 1)
            remove(it, it + 1)
        }
        //When
        sync()
        //Then
        assertSize(0)
        repeat(count) {
            assertRemoval(it, false)
        }
    }

    private fun insert(vararg values: Pair<Int, Int>) {
        values.forEach {
            bag.insert(it.first, it.second)
        }
    }

    private fun insert(value: Int, index: Int) {
        insert(Pair(value, index))
    }

    private fun set(value: Int, index: Int) {
        bag.set(value, index)
    }

    private fun remove(value: Int, index: Int) {
        bag.remove(value, index)
    }

    private fun sync() {
        bag.sync()
    }

    private fun assertSize(expected: Int) {
        assertThat(bag.size).isEqualTo(expected)
    }

    private fun assertInserted(entity: Int, expected: Boolean) {
        assertThat(bag.needsInsert(entity)).isEqualTo(expected)
    }

    private fun assertRemoval(entity: Int, expected: Boolean) {
        assertThat(bag.needsRemoval(entity)).isEqualTo(expected)
    }
}