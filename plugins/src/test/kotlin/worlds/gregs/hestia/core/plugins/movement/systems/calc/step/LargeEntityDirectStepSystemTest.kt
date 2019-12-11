package worlds.gregs.hestia.core.plugins.movement.systems.calc.step

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import worlds.gregs.hestia.core.plugins.entity.systems.step
import worlds.gregs.hestia.core.plugins.movement.systems.calc.DirectStepSystem
import worlds.gregs.hestia.core.plugins.movement.systems.calc.PathTester

internal class LargeEntityDirectStepSystemTest : PathTester(false, DirectStepSystem()) {

    @BeforeEach
    override fun setup() {
        super.setup()
        reset(0, 0, 1, 2)
    }

    @Test
    fun `Navigate vertically challenged entity straight line without obstacles`() {
        canvas(0 until 4, 0 until 2)

        val expected = listOf(
                intArrayOf(1, 0),
                intArrayOf(2, 0),
                intArrayOf(3, 0)
        )

        assertPath(3, 0, expected)
    }

    @Test
    fun `Navigate vertical entity straight onto an obstacle and walk up to`() {
        canvas(0 until 4, 0 until 2)
        block(3, 0)

        val expected = listOf(
                intArrayOf(1, 0),
                intArrayOf(2, 0)
        )

        assertPath(3, 0, expected)
    }

    @Test
    fun `Navigate vertical entity straight onto an offset obstacle and only walk up to`() {
        canvas(0 until 4, 0 until 2)
        block(3, 1)

        val expected = listOf(
                intArrayOf(1, 0),
                intArrayOf(2, 0)
        )

        assertPath(3, 0, expected)
    }

    @Test
    fun `Attempt to navigate vertical entity straight to a clear line of sight but adjacent obstacle and only walk up to`() {
        canvas(0 until 4, 0 until 3)
        block(3, 2)

        val expected = listOf(
                intArrayOf(1, 1),
                intArrayOf(2, 1)
        )

        assertPath(3, 1, expected)
    }

    @Test
    fun `Navigate vertical entity straight vertically between obstacle gap`() {
        canvas(0 until 4, 0 until 5)
        block(0, 1)
        block(2, 1)

        start(1, 0)

        val expected = listOf(
                intArrayOf(1, 1),
                intArrayOf(1, 2),
                intArrayOf(1, 3)
        )

        assertPath(1, 3, expected)
    }

    @Test
    fun `Navigate large entity diagonally between single tile gap`() {
        canvas(0 until 6, 0 until 6)
        block(1, 3)
        block(3, 1)

        size(2, 2)

        val expected = listOf(
                intArrayOf(1, 1),
                intArrayOf(2, 2),
                intArrayOf(3, 3),
                intArrayOf(4, 4)
        )

        assertPath(4, 4, expected)
    }

    @Test
    fun `Fail to navigate fat entity diagonally between obstacle gap of 2 tiles`() {
        canvas(0 until 5, 0 until 5)
        block(1, 3)
        block(3, 1)

        size(3, 3)
        assertThrows<NoPath> {
            assertPath(4, 4)
        }
    }

    @Test
    fun `Navigate fat entity diagonally between obstacle gap of 2 tiles`() {
        canvas(0 until 7, 0 until 7)
        block(0, 3)
        block(3, 0)

        size(3, 3)

        val expected = listOf(
                intArrayOf(1, 1),
                intArrayOf(2, 2),
                intArrayOf(3, 3),
                intArrayOf(4, 4)
        )

        assertPath(4, 4, expected)
    }

    private fun assertPath(targetX: Int, targetY: Int, expected: List<IntArray>? = null, display: Boolean = false) {
        assertPath(expected, display) {
            it.step(targetX, targetY)
        }
    }
}