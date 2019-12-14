package worlds.gregs.hestia.core.world.movement.logic.systems.calc.beside

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import worlds.gregs.hestia.core.world.movement.logic.systems.calc.PathTester
import worlds.gregs.hestia.core.world.movement.logic.systems.calc.StepBesideSystem
import worlds.gregs.hestia.core.world.movement.model.components.calc.Beside

internal class StepBesideSystemTest : PathTester(false, StepBesideSystem()) {

    @BeforeEach
    override fun setup() {
        super.setup()
        reset(0, 0, 1, 1)
    }

    @Test
    fun `Step straight next to tile`() {
        canvas(0 until 4, 0 until 1)

        val expected = listOf(
                intArrayOf(1, 0),
                intArrayOf(2, 0)
        )

        assertPath(3, 0, expected)
    }

    @Test
    fun `Step straight onto obstacle`() {

        canvas(0 until 4, 0 until 1)
        block(3, 0)


        val expected = listOf(
                intArrayOf(1, 0),
                intArrayOf(2, 0)
        )

        assertPath(3, 0, expected)
    }

    @Test
    fun `Step diagonal`() {
        canvas(0 until 3, 0 until 3)

        val expected = listOf(
                intArrayOf(1, 1)
        )

        assertPath(2, 2, expected, true)
    }

    @Test
    fun `Step diagonal close`() {
        canvas(0 until 3, 0 until 3)

        val expected = listOf(
                intArrayOf(1, 1)
        )

        assertPath(1, 1, expected, true)
    }

    @Test
    fun `Step diagonal beside`() {
        canvas(0 until 3, 0 until 3)

        val expected = listOf(
                intArrayOf(1, 1),
                intArrayOf(2, 1)
        )

        assertPath(2, 2, expected, beside = true)
    }

    @Test
    fun `Step diagonal onto obstacle`() {
        canvas(0 until 3, 0 until 3)
        block(2, 2)

        val expected = listOf(
                intArrayOf(1, 1)
        )

        assertPath(2, 2, expected)
    }

    @Test
    fun `Step diagonal beside obstacle`() {
        canvas(0 until 3, 0 until 3)
        block(2, 2)

        val expected = listOf(
                intArrayOf(1, 1),
                intArrayOf(2, 1)
        )

        assertPath(2, 2, expected, beside = true)
    }

    @Test
    fun `Step at an angle`() {
        canvas(0 until 5, 0 until 3)

        val expected = listOf(
                intArrayOf(1, 1),
                intArrayOf(2, 2),
                intArrayOf(3, 2)
        )

        assertPath(4, 2, expected)
    }

    @Test
    fun `Can't step corner without calculation`() {
        canvas(0 until 3, 0 until 3)
        block(1, 1)

        start(1, 2)

        assertThrows<NoPath> {
            assertPath(0, 0, calculate = false)
        }
    }

    @Test
    fun `Step corner with calculation`() {
        canvas(0 until 3, 0 until 3)
        block(1, 1)

        start(1, 2)
        val expected = listOf(
                intArrayOf(0, 2)
        )

        assertPath(0, 0, expected, calculate = true)
    }

    @Test
    fun `Step tall target south`() {
        canvas(0 until 1, 0 until 4)

        start(0, 3)

        val expected = listOf(
                intArrayOf(0, 2)
        )

        assertPath(0, 0, expected, height = 2)
    }

    @Test
    fun `No step above tall target`() {
        /*
            FIXME do we want to keep beside a target already beside walking onto the target or not?
            Won't affect mobs & stalking as they can't walk on the target anyway
            Player following doesn't use beside anymore
            Initial player follow does use beside, but not if already beside (early check), if it were changed, we could remove this check
            TODO What about player combat?
         */

        /*canvas(0 until 1, 0 until 4)
        print()

        start(0, 2)

        assertThrows<NoPath> {
            assertPath(0, 0, null, true, height = 2)
        }*/
    }

    @Test
    fun `No step beside wide target`() {
        //FIXME
        /*canvas(0 until 3, 0 until 1)

        start(2, 0)

        assertThrows<NoPath> {
            assertPath(0, 0, width = 2)
        }*/
    }

    @Test
    fun `No step if already beside`() {
        //FIXME
        /*canvas(0 until 3, 0 until 3)

        assertThrows<NoPath> {
            assertPath(0, 1)
        }*/
    }

    @Test
    fun `Step wide target west`() {
        canvas(0 until 4, 0 until 1)

        start(3, 0)

        val expected = listOf(
                intArrayOf(2, 0)
        )

        assertPath(0, 0, expected, width = 2)
    }

    @Test
    fun `Step diagonal large target`() {
        canvas(0 until 4, 0 until 4)

        start(3, 3)

        val expected = listOf(
                intArrayOf(2, 2)
        )

        assertPath(0, 0, expected, width = 2, height = 2)
    }

    @Test
    fun `Step diagonal beside large target`() {
        canvas(0 until 4, 0 until 4)

        start(3, 3)

        val expected = listOf(
                intArrayOf(2, 2),
                intArrayOf(1, 2)
        )

        assertPath(0, 0, expected, beside = true, width = 2, height = 2)
    }

    @Test
    fun `Step diagonal tall target`() {
        canvas(0 until 4, 0 until 4)

        start(3, 3)

        val expected = listOf(
                intArrayOf(2, 2),
                intArrayOf(1, 1)
        )

        assertPath(0, 0, expected, height = 2)
    }

    @Test
    fun `Step diagonal wide target`() {
        canvas(0 until 4, 0 until 4)

        start(3, 3)

        val expected = listOf(
                intArrayOf(2, 2),
                intArrayOf(1, 1)
        )

        assertPath(0, 0, expected, width = 2)
    }

    @Test
    fun `Step maximum`() {
        canvas(0 until 4, 0 until 1)

        val expected = listOf(
                intArrayOf(1, 0),
                intArrayOf(2, 0)
        )

        assertPath(4, 0, expected, max = 2)
    }

    @Test
    fun `Step without checking`() {
        canvas(0 until 4, 0 until 1)
        block(1, 0)

        val expected = listOf(
                intArrayOf(1, 0),
                intArrayOf(2, 0)
        )

        assertPath(3, 0, expected, check = false)
    }


    private fun assertPath(targetX: Int, targetY: Int, expected: List<IntArray>? = null, display: Boolean = false, max: Int = -1, width: Int = 1, height: Int = 1, check: Boolean = true, calculate: Boolean = false, beside: Boolean = false) {
        assertPath(expected, display) {
            it.edit().add(Beside(targetX, targetY, max, width, height, check, calculate, beside))
        }
    }
}