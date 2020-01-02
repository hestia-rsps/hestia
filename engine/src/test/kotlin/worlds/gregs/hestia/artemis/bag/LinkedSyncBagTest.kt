package worlds.gregs.hestia.artemis.bag

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.artemis.bag.map.EntitySyncBag
import worlds.gregs.hestia.artemis.bag.map.LinkedSyncBag

internal class LinkedSyncBagTest : SyncBagTester() {

    override fun bag(): EntitySyncBag {
        return LinkedSyncBag(40)
    }

    @Test
    fun `For each iterates entity ids in insertion order`() {
        //Given
        set(1000, 2)
        set(10, 1)
        set(100, 3)
        //Then
        assertForEach(1000, 10, 100)
    }

    @Test
    fun `For each indexed iterates in insertion order`() {
        //Given
        set(1000, 2)
        set(10, 1)
        set(100, 3)
        //Then
        assertForEachIndexed(Pair(1000, 2), Pair(10, 1), Pair(100, 3))
    }

    private fun assertForEach(vararg ids: Int) {
        var index = 0
        bag.forEach {
            assertThat(it).isEqualTo(ids[index++])
        }
    }

    private fun assertForEachIndexed(vararg values: Pair<Int, Int>) {
        var index = 0
        bag.forEachIndexed { i, entity ->
            val value = values[index++]
            assertThat(entity).isEqualTo(value.first)
            assertThat(i).isEqualTo(value.second)
        }
    }
}