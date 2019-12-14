package worlds.gregs.hestia.artemis.bag

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.artemis.bag.map.EntitySyncBag
import worlds.gregs.hestia.artemis.bag.map.OrderedSyncBag

internal class OrderedSyncBagTest : SyncBagTester() {

    override fun bag(): EntitySyncBag {
        return OrderedSyncBag(4, 40)
    }

    @Test
    fun `For each iterates entity ids in index order`() {
        //Given
        set(1000, 2)
        set(10, 1)
        set(100, 3)
        //Then
        assertForEach(10, 1000, 100)
    }

    @Test
    fun `For each indexed iterates in index order`() {
        //Given
        set(1000, 2)
        set(10, 1)
        set(100, 3)
        //Then
        assertForEachIndexed(Pair(null, 0), Pair(10, 1), Pair(1000, 2), Pair(100, 3))
    }

    private fun assertForEach(vararg ids: Int) {
        var index = 0
        bag.forEach {
            Assertions.assertThat(it).isEqualTo(ids[index++])
        }
    }

    private fun assertForEachIndexed(vararg values: Pair<Int?, Int>) {
        var index = 0
        bag.forEachIndexed { i, entity ->
            val value = values[index++]
            Assertions.assertThat(entity).isEqualTo(value.first)
            Assertions.assertThat(i).isEqualTo(value.second)
        }
    }
}