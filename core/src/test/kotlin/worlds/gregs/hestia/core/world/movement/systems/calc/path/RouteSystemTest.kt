package worlds.gregs.hestia.core.world.movement.systems.calc.path

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import worlds.gregs.hestia.core.world.movement.components.calc.Path
import worlds.gregs.hestia.core.world.movement.strategies.FixedTileStrategy
import worlds.gregs.hestia.core.world.movement.systems.calc.PathFinderSystem
import worlds.gregs.hestia.core.world.movement.systems.calc.PathSystem
import worlds.gregs.hestia.core.world.movement.systems.calc.PathTester

internal class RouteSystemTest : PathTester(true, PathSystem(), PathFinderSystem()) {


    @BeforeEach
    override fun setup() {
        super.setup()
        reset(0, 0, 1, 1)
    }

    @Test
    fun `Path around an obstacle`() {
        canvas(0 until 4, 0 until 2)
        block(1, 0)

        val expected = listOf(
                intArrayOf(0, 1),
                intArrayOf(1, 1),
                intArrayOf(2, 1),
                intArrayOf(2, 0)
        )

        assertPath(2, 0, expected)
    }

    @Test
    fun `Path diagonal`() {
        canvas(0 until 5, 0 until 5)

        val expected = listOf(
                intArrayOf(1, 1),
                intArrayOf(2, 2),
                intArrayOf(3, 3),
                intArrayOf(4, 4)
        )

        assertPath(4, 4, expected)
    }

    @Test
    fun `Path right around a single obstacle`() {
        canvas(0 until 4, 0 until 3)
        block(1, 1)

        start(0, 1)

        val expected = listOf(
                intArrayOf(0, 0),
                intArrayOf(1, 0),
                intArrayOf(2, 0),
                intArrayOf(2, 1)
        )

        assertPath(2, 1, expected)
    }

    @Test
    fun `Path left around a single obstacle`() {
        canvas(0 until 4, 0 until 3)
        block(1, 1)

        start(2, 1)

        val expected = listOf(
                intArrayOf(2, 0),
                intArrayOf(1, 0),
                intArrayOf(0, 0),
                intArrayOf(0, 1)
        )

        assertPath(0, 1, expected)
    }

    @Test
    fun `Path down around a single obstacle`() {
        canvas(0 until 4, 0 until 3)
        block(1, 1)

        start(1, 2)

        val expected = listOf(
                intArrayOf(0, 2),
                intArrayOf(0, 1),
                intArrayOf(0, 0),
                intArrayOf(1, 0)
        )

        assertPath(1, 0, expected)
    }

    @Test
    fun `Path up around a single obstacle`() {
        canvas(0 until 4, 0 until 3)
        block(1, 1)

        start(1, 0)

        val expected = listOf(
                intArrayOf(0, 0),
                intArrayOf(0, 1),
                intArrayOf(0, 2),
                intArrayOf(1, 2)
        )

        assertPath(1, 2, expected)
    }

    @Test
    fun `Path complex`() {
        canvas(0 until 5, 0 until 5)
        block(0 until 4, 1)
        block(1 until 5, 3)

        val expected = listOf(
                intArrayOf(1, 0),
                intArrayOf(2, 0),
                intArrayOf(3, 0),
                intArrayOf(4, 0),
                intArrayOf(4, 1),
                intArrayOf(4, 2),
                intArrayOf(3, 2),
                intArrayOf(2, 2),
                intArrayOf(1, 2),
                intArrayOf(0, 2),
                intArrayOf(0, 3),
                intArrayOf(0, 4),
                intArrayOf(1, 4),
                intArrayOf(2, 4),
                intArrayOf(3, 4),
                intArrayOf(4, 4)
        )

        assertPath(4, 4, expected)
    }

    @Test
    fun `Path not alternative`() {
        canvas(0 until 4, 0 until 1)
        block(2, 0)

        assertThrows<NoPath> {
            assertPath(3, 0, null, true, false)
        }
    }

    private fun assertPath(targetX: Int, targetY: Int, expected: List<IntArray>? = null, display: Boolean = false, alternative: Boolean = true) {
        assertPath(expected, display) {
            it.edit().add(Path(FixedTileStrategy(targetX, targetY), true, alternative))
        }
    }
}