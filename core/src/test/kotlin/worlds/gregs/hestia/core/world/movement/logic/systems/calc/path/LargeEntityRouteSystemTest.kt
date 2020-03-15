package worlds.gregs.hestia.core.world.movement.logic.systems.calc.path

import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.world.movement.logic.strategies.FixedTileStrategy
import worlds.gregs.hestia.core.world.movement.logic.systems.calc.PathFinderSystem
import worlds.gregs.hestia.core.world.movement.logic.systems.calc.PathSystem
import worlds.gregs.hestia.core.world.movement.logic.systems.calc.PathTester
import worlds.gregs.hestia.core.world.movement.model.components.calc.Path

@ExtendWith(MockKExtension::class)
internal class LargeEntityRouteSystemTest : PathTester(true, PathSystem(), PathFinderSystem(), mockk<Tasks>(relaxed = true)) {

    @BeforeEach
    override fun setup() {
        super.setup()
        reset(0 ,0, 1, 2)
    }

    @Test
    fun `Path vertically challenged entity around an obstacles`() {
        canvas(0 until 4, 0 until 3)
        block(1, 0)

        val expected = listOf(
                intArrayOf(1, 1),
                intArrayOf(2, 0)
        )

        assertPath(2, 0, expected)
    }

    @Test
    fun `Path diagonal`() {
        canvas(0 until 5, 0 until 5)

        val expected = listOf(
                intArrayOf(1, 0),
                intArrayOf(2, 1),
                intArrayOf(3, 2),
                intArrayOf(4, 3)
        )

        assertPath(4, 4, expected)
    }

    @Test
    fun `Path right around a single obstacle`() {
        canvas(0 until 3, 0 until 4)
        block(1, 1)

        start(0, 1)

        val expected = listOf(
                intArrayOf(1, 2),
                intArrayOf(2, 1)
        )

        assertPath(2, 1, expected)
    }

    @Test
    fun `Path left around a single obstacle`() {
        canvas(0 until 3, 0 until 4)
        block(1, 1)

        start(2, 1)
        val expected = listOf(
                intArrayOf(1, 2),
                intArrayOf(0, 1)
        )

        assertPath(0, 1, expected)
    }

    @Test
    fun `Path down around a single obstacle`() {
        canvas(0 until 2, 0 until 5)
        block(1, 2)

        start(1, 3)
        val expected = listOf(
                intArrayOf(0, 2),
                intArrayOf(0, 1),
                intArrayOf(1, 0)
        )
        assertPath(1, 0, expected)
    }

    @Test
    fun `Path up around a single obstacle`() {
        canvas(0 until 2, 0 until 5)
        block(1, 2)

        start(1, 0)
        val expected = listOf(
                intArrayOf(0, 1),
                intArrayOf(0, 2),
                intArrayOf(1, 3)
        )

        assertPath(1, 3, expected)
    }

    @Test
    fun `Path complex`() {
        canvas(0 until 5, 0 until 8)
        block(0 until 4, 2)
        block(1 until 5, 5)

        val expected = listOf(
                intArrayOf(1, 0),
                intArrayOf(2, 0),
                intArrayOf(3, 0),
                intArrayOf(4, 1),
                intArrayOf(4, 2),
                intArrayOf(3, 3),
                intArrayOf(2, 3),
                intArrayOf(1, 3),
                intArrayOf(0, 4),
                intArrayOf(0, 5),
                intArrayOf(1, 6),
                intArrayOf(2, 6),
                intArrayOf(3, 6),
                intArrayOf(4, 6)
        )

        assertPath(4, 7, expected)
    }

    private fun assertPath(targetX: Int, targetY: Int, expected: List<IntArray>? = null, display: Boolean = false) {
        assertPath(expected, display) {
            it.edit().add(Path(FixedTileStrategy(targetX, targetY)))
        }
    }
}