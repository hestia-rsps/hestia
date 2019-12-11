package worlds.gregs.hestia.core.plugins.movement.systems.calc.step

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import worlds.gregs.hestia.core.plugins.entity.systems.step
import worlds.gregs.hestia.core.plugins.movement.systems.calc.DirectStepSystem
import worlds.gregs.hestia.core.plugins.movement.systems.calc.PathTester

internal class StepTest : PathTester(false, DirectStepSystem()) {

    @BeforeEach
    override fun setup() {
        super.setup()
        reset(0, 0, 1, 1)
    }

    @Test
    fun `Navigates a straight line without obstacles`() {
        canvas(0 until 4, 0 until 1)

        val expected = listOf(
                intArrayOf(1, 0),
                intArrayOf(2, 0),
                intArrayOf(3, 0)
        )

        assertPath(3, 0, expected)
    }

    @Test
    fun `Navigate straight with a maximum distance`() {
        canvas(0 until 5, 0 until 1)

        val expected = listOf(
                intArrayOf(1, 0),
                intArrayOf(2, 0)
        )

        assertPath(4, 0, expected, 2)
    }

    @Test
    fun `Attempt to navigate onto an obstacle but only walk up to it`() {

        canvas(0 until 5, 0 until 1)
        block(4, 0)

        val expected = listOf(
                intArrayOf(1, 0),
                intArrayOf(2, 0),
                intArrayOf(3, 0)
        )

        assertPath(4, 0, expected = expected)
    }

    @Test
    fun `Navigate straight onto a blocked tile but without checking clipping`() {
        canvas(0 until 5, 0 until 1)
        block(4, 0)

        val expected = listOf(
                intArrayOf(1, 0),
                intArrayOf(2, 0),
                intArrayOf(3, 0),
                intArrayOf(4, 0)
        )

        assertPath(4, 0, expected, check = false)
    }

    @Test
    fun `Attempt to navigate straight over a blocked tile but walk up to it`() {
        canvas(0 until 5, 0 until 1)
        block(3, 0)

        val expected = listOf(
                intArrayOf(1, 0),
                intArrayOf(2, 0)
        )

        assertPath(4, 0, expected)
    }

    @Test
    fun `Navigate straight over a blocked tile without checking clipping`() {

        canvas(0 until 5, 0 until 1)
        block(3, 0)


        val expected = listOf(
                intArrayOf(1, 0),
                intArrayOf(2, 0),
                intArrayOf(3, 0),
                intArrayOf(4, 0)
        )

        assertPath(4, 0, expected, check = false)
    }

    @Test
    fun `Navigate off of a blocked tile`() {

        canvas(0 until 2, 0 until 2)
        block(0, 0)


        val expected = listOf(
                intArrayOf(1, 0)
        )

        assertPath(1, 0, expected)
    }

    @Test
    fun `Navigate diagonally`() {
        canvas(0 until 3, 0 until 3)

        val expected = listOf(
                intArrayOf(1, 1),
                intArrayOf(2, 2)
        )

        assertPath(2, 2, expected)
    }

    @Test
    fun `Attempt to navigate diagonally onto an obstacle but only walk up to it`() {
        canvas(0 until 3, 0 until 3)
        block(2, 2)

        val expected = listOf(
                intArrayOf(1, 1)
        )

        assertPath(2, 2, expected)
    }

    @Test
    fun `Attempt to navigate diagonally over a blocked tile but only walk up to it`() {
        canvas(0 until 4, 0 until 4)
        block(2, 2)

        val expected = listOf(
                intArrayOf(1, 1)
        )

        assertPath(3, 3, expected)
    }

    @Test
    fun `Navigate diagonally north-east`() {
        canvas(0 until 2, 0 until 2)

        val expected = listOf(
                intArrayOf(1, 1)
        )

        assertPath(1, 1, expected)
    }

    @Test
    fun `Navigate diagonally north-west`() {
        canvas(0 until 2, 0 until 2)

        start(1, 0)

        val expected = listOf(
                intArrayOf(0, 1)
        )

        assertPath(0, 1, expected)
    }

    @Test
    fun `Navigate diagonally south-east`() {
        canvas(0 until 2, 0 until 2)

        start(0, 1)

        val expected = listOf(
                intArrayOf(1, 0)
        )

        assertPath(1, 0, expected)
    }

    @Test
    fun `Navigate diagonally south-west`() {
        canvas(0 until 2, 0 until 2)

        start(1, 1)

        val expected = listOf(
                intArrayOf(0, 0)
        )

        assertPath(0, 0, expected)
    }

    @Test
    fun `Fail to navigate diagonally north-east because of an obstacle north-west`() {
        canvas(0 until 2, 0 until 2)
        block(0, 1)

        assertThrows<NoPath> {
            assertPath(1, 1)
        }
    }

    @Test
    fun `Fail to navigate diagonally south-west because of an obstacle north-west`() {
        canvas(0 until 2, 0 until 2)
        block(0, 1)

        start(1, 1)

        assertThrows<NoPath> {
            assertPath(0, 0)
        }
    }

    @Test
    fun `Fail to navigate diagonally south-east because of an obstacle north-east`() {
        canvas(0 until 2, 0 until 2)
        block(1, 1)

        start(0, 1)

        assertThrows<NoPath> {
            assertPath(1, 0)
        }
    }

    @Test
    fun `Fail to navigate diagonally north-west because of an obstacle north-east`() {
        canvas(0 until 2, 0 until 2)
            block(1, 1)


        start(1, 0)

        assertThrows<NoPath> {
            assertPath(0, 1)
        }
    }

    @Test
    fun `Navigate two tiles diagonally with the second blocked by an obstacle so only walk to first tile`() {
        canvas(0 until 3, 0 until 3)
        block(2, 1)


        val expected = listOf(
                intArrayOf(1, 1)
        )

        assertPath(2, 2, expected)
    }

    @Test
    fun `Navigate diagonally with a maximum distance`() {
        canvas(0 until 5, 0 until 5)

        val expected = listOf(
                intArrayOf(1, 1),
                intArrayOf(2, 2)
        )

        assertPath(4, 4, expected, 2)
    }

    private fun assertPath(targetX: Int, targetY: Int, expected: List<IntArray>? = null, max: Int = -1, check: Boolean = true, display: Boolean = false) {
        assertPath(expected, display) {
            it.step(targetX, targetY, max, check)
        }
    }
}