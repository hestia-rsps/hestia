package worlds.gregs.hestia.core.entity.item.container.logic

import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import worlds.gregs.hestia.core.entity.item.container.api.Container
import worlds.gregs.hestia.core.entity.item.container.model.Item

@ExtendWith(MockKExtension::class)
internal class ContainerSortTest {

    @Test
    fun `Sort mixed`() {
        val item = items(null, TYPE_1 to 1, null, TYPE_2 to 2, null, TYPE_3 to 3, null)
        assertArrayEquals(item.sort(), items(TYPE_1 to 1, TYPE_2 to 2, TYPE_3 to 3, null, null, null, null))
    }

    @Test
    fun `Sort some`() {
        val item = items(TYPE_3 to 3, TYPE_2 to 2, TYPE_1 to 1)
        assertArrayEquals(item.sort(), items(TYPE_3 to 3, TYPE_2 to 2, TYPE_1 to 1))
    }

    @Test
    fun `Sort none`() {
        val item = arrayOfNulls<Item?>(4)
        assertArrayEquals(item.sort(), arrayOfNulls<Item?>(4))
    }

    companion object {

        private fun items(vararg ids: Pair<Int, Int>?): Container {
            return ids.map { if (it != null) Item(it.first, it.second) else null }.toTypedArray()
        }

        const val TYPE_1 = 115
        const val TYPE_2 = 215
        const val TYPE_3 = 315
    }
}