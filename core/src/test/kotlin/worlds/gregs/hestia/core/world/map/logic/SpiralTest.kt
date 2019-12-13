package worlds.gregs.hestia.core.world.map.logic

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.function.BiPredicate

class SpiralTest {
    private lateinit var points: ArrayList<Pair<Int, Int>>
    private var x = 0//Start point
    private var y = 0//Start point
    private var width = 1//Start size
    private var height = 1//Start size
    //assert("[(0, 0 )]")//), (

    @BeforeEach
    fun setup() {
        points = ArrayList()
        x = 0
        y = 0
        width = 1
        height = 1
    }

    @Test
    fun `Spiral inside out size 1x1 radius 1`() {
        //Given
        start(1, 2)
        //When
        spiralOut(1)
        //Then
        assert("[(1, 2), (0, 2), (0, 3), (1, 3), (2, 3), (2, 2), (2, 1), (1, 1), (0, 1)]")
    }

    @Test
    fun `Spiral inside out size 1x2 radius 1`() {
        //Given
        start(1, 2)
        size(1, 2)
        //When
        spiralOut(1)
        //Then
        assert("[(1, 2), (0, 2), (0, 3), (1, 3), (2, 3), (2, 2), (2, 1), (1, 1), (0, 1), (0, 4), (1, 4), (2, 4)]")
    }

    @Test
    fun `Spiral inside out size 2x1 radius 1`() {
        //Given
        start(1, 2)
        size(2, 1)
        //When
        spiralOut(1)
        //Then
        assert("[(1, 2), (0, 2), (0, 3), (1, 3), (2, 3), (2, 2), (2, 1), (1, 1), (0, 1), (3, 3), (3, 2), (3, 1)]")
    }

    @Test
    fun `Spiral inside out size 2x2 radius 1`() {
        //Given
        start(2, 2)
        size(2, 2)
        //When
        spiralOut(1)
        //Then
        assert("[(2, 2), (1, 2), (1, 3), (2, 3), (3, 3), (3, 2), (3, 1), (2, 1), (1, 1), (0, 1), (0, 2), (0, 3), (0, 4), (1, 4), (2, 4), (3, 4)]")
    }

    @Test
    fun `Spiral inside out size 1x1 radius 2`() {
        //Given
        start(2, 3)
        size(1, 1)
        //When
        spiralOut(2)
        //Then
        assert("[(2, 3), (1, 3), (1, 4), (2, 4), (3, 4), (3, 3), (3, 2), (2, 2), (1, 2), (0, 2), (0, 3), (0, 4), (0, 5), (1, 5), (2, 5), (3, 5), (4, 5), (4, 4), (4, 3), (4, 2), (4, 1), (3, 1), (2, 1), (1, 1), (0, 1)]")
    }

    @Test
    fun `Spiral outside in radius 2`() {
        //Given
        start(2, 3)
        //When
        spiralIn(2)
        //Then
        assert("[(0, 1), (1, 1), (2, 1), (3, 1), (4, 1), (4, 2), (4, 3), (4, 4), (4, 5), (3, 5), (2, 5), (1, 5), (0, 5), (0, 4), (0, 3), (0, 2), (1, 2), (2, 2), (3, 2), (3, 3), (3, 4), (2, 4), (1, 4), (1, 3), (2, 3)]")
    }

    @Test
    fun `Spiral outside in radius 1`() {
        //Given
        start(1, 2)
        //When
        spiralIn(1)
        //Then
        assert("[(0, 1), (1, 1), (2, 1), (2, 2), (2, 3), (1, 3), (0, 3), (0, 2), (1, 2)]")
    }

    private fun start(x: Int, y: Int) {
        this.x = x
        this.y = y
    }

    private fun size(width: Int, height: Int) {
        this.width = width
        this.height = height
    }

    private fun spiralOut(radius: Int) {
        Spiral.outwards(x, y, width, height, radius, BiPredicate { x, y -> points.add(Pair(x, y)) })
    }

    private fun spiralIn(radius: Int) {
        Spiral.inwards(x, y, radius, BiPredicate { x, y -> points.add(Pair(x, y)) })
    }

    private fun assert(expected: String) {
        assertEquals(expected, points.toString())
    }

}