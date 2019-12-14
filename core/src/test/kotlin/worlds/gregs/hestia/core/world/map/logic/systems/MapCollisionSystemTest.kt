package worlds.gregs.hestia.core.world.map.logic.systems

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.core.world.map.api.TileClipping
import worlds.gregs.hestia.core.world.movement.logic.systems.calc.MovementTester
import worlds.gregs.hestia.core.display.update.model.Direction
import worlds.gregs.hestia.core.display.update.model.Direction.*
import worlds.gregs.hestia.artemis.getSystem

internal class MapCollisionSystemTest : MovementTester(false) {

    private var traverse: TileClipping? = null
    private var blocked: Array<Direction> = emptyArray()

    @BeforeEach
    override fun setup() {
        super.setup()
        traverse = world.getSystem(MapCollisionSystem::class)
        reset(0, 0, 1, 1)
        blocked = emptyArray()
    }

    @Test
    fun `Check bottom left`() {
        expected(SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST)

        //Single
        //Given empty 2x2 grid
        singleCanvas()

        //When start at 0, 0 and size is 1x1
        start(0, 0)

        //Then directions are blocked
        assertBlockedDirections()

        //Square
        //Given 6x6 grid
        squareCanvas()

        //When start at 0, 0 and size is 2x2
        start(0, 0)

        //Then directions are blocked
        assertBlockedDirections()

        //Rectangle
        //Given 3x4 grid
        rectCanvas()

        //When start at 0, 0 and size is 2x3
        start(0, 0)

        //Then directions are blocked
        assertBlockedDirections()
    }

    @Test
    fun `Check top right`() {
        expected(NORTH, NORTH_EAST, EAST, SOUTH_EAST, NORTH_WEST)
        //Single
        singleCanvas()

        start(4, 4)

        assertBlockedDirections()

        //Square
        squareCanvas()

        start(4, 4)

        assertBlockedDirections()

        //Rectangle
        rectCanvas()

        start(4, 6)

        assertBlockedDirections()
    }

    @Test
    fun `Check bottom right`() {
        expected(NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST)
        //Single
        singleCanvas()

        start(4, 0)

        assertBlockedDirections()

        //Square
        squareCanvas()

        start(4, 0)

        assertBlockedDirections()

        //Rectangle
        rectCanvas()

        start(4, 0)

        assertBlockedDirections()
    }

    @Test
    fun `Check top left`() {
        expected(NORTH, NORTH_EAST, SOUTH_WEST, WEST, NORTH_WEST)
        //Single
        singleCanvas()

        start(0, 4)

        assertBlockedDirections()

        //Square
        squareCanvas()

        start(0, 4)

        assertBlockedDirections()

        //Rectangle
        rectCanvas()

        start(0, 6)

        assertBlockedDirections()
    }

    @Test
    fun `Check obstacle north`() {
        //Single
        singleCanvas()
        block(1, 2)

        assertBlockedDirections(NORTH, NORTH_EAST, NORTH_WEST)

        expected(NORTH, NORTH_WEST)//Larger entities can cut corners

        //Square
        squareCanvas()
        block(2, 4)

        assertBlockedDirections()

        //Rectangle
        rectCanvas()
        block(2, 6)

        assertBlockedDirections()
    }

    @Test
    fun `Check obstacle north offset`() {
        expected(NORTH, NORTH_EAST)

        //Square
        squareCanvas()
        block(3, 4)

        assertBlockedDirections()

        //Rectangle
        rectCanvas()
        block(3, 6)

        assertBlockedDirections()
    }

    @Test
    fun `Check obstacle north-east`() {
        expected(NORTH_EAST)
        //Single
        singleCanvas()
        block(2, 2)

        assertBlockedDirections()

        //Square
        squareCanvas()
        block(4, 4)

        assertBlockedDirections()

        //Rectangle
        rectCanvas()
        block(4, 6)

        assertBlockedDirections()
    }

    @Test
    fun `Check obstacle east`() {
        //Single
        singleCanvas()
        block(2, 1)

        assertBlockedDirections(NORTH_EAST, EAST, SOUTH_EAST)

        expected(NORTH_EAST, EAST)//Larger entities can cut corners

        //Square
        squareCanvas()
        block(4, 3)

        assertBlockedDirections()

        //Rectangle
        rectCanvas()
        block(4, 5)

        assertBlockedDirections()
    }

    @Test
    fun `Check obstacle east offset`() {
        expected(EAST, SOUTH_EAST)

        //Square
        squareCanvas()
        block(4, 2)

        assertBlockedDirections()

        //Rectangle
        rectCanvas()
        block(4, 3)

        assertBlockedDirections()
    }

    @Test
    fun `Check obstacle south-east`() {
        expected(SOUTH_EAST)
        //Single
        singleCanvas()
        block(2, 0)

        assertBlockedDirections()

        //Square
        squareCanvas()
        block(4, 1)

        assertBlockedDirections()

        //Rectangle
        rectCanvas()
        block(4, 2)

        assertBlockedDirections()
    }

    @Test
    fun `Check obstacle south`() {
        //Single
        singleCanvas()
        block(1, 0)

        assertBlockedDirections(SOUTH_EAST, SOUTH, SOUTH_WEST)

        expected(SOUTH_EAST, SOUTH)//Larger entities can cut corners

        //Square
        squareCanvas()
        block(3, 1)

        assertBlockedDirections()

        //Rectangle
        rectCanvas()
        block(3, 2)

        assertBlockedDirections()
    }

    @Test
    fun `Check obstacle south offset`() {
        expected(SOUTH_WEST, SOUTH)

        //Square
        squareCanvas()
        block(2, 1)

        assertBlockedDirections()

        //Rectangle
        rectCanvas()
        block(2, 2)

        assertBlockedDirections()
    }

    @Test
    fun `Check obstacle south-west`() {
        expected(SOUTH_WEST)
        //Single
        singleCanvas()
        block(0, 0)

        assertBlockedDirections()

        //Square
        squareCanvas()
        block(1, 1)

        assertBlockedDirections()

        //Rectangle
        rectCanvas()
        block(1, 2)

        assertBlockedDirections()
    }

    @Test
    fun `Check obstacle west`() {
        //Single
        singleCanvas()
        block(0, 1)

        assertBlockedDirections(SOUTH_WEST, WEST, NORTH_WEST)

        expected(SOUTH_WEST, WEST)//Larger entities can cut corners

        //Square
        squareCanvas()
        block(1, 2)

        assertBlockedDirections()

        //Rectangle
        rectCanvas()
        block(1, 3)

        assertBlockedDirections()
    }

    @Test
    fun `Check obstacle west offset`() {
        expected(NORTH_WEST, WEST)
        //Square
        squareCanvas()
        block(1, 3)

        assertBlockedDirections()

        //Rectangle
        rectCanvas()
        block(1, 5)

        assertBlockedDirections()
    }

    @Test
    fun `Check obstacle north-west`() {
        expected(NORTH_WEST)
        //Single
        singleCanvas()
        block(0, 2)

        assertBlockedDirections()

        //Square
        squareCanvas()
        block(1, 4)

        assertBlockedDirections()

        //Rectangle
        rectCanvas()
        block(1, 6)

        assertBlockedDirections()
    }

    @Test
    fun `Check wall north`() {
        expected(NORTH, NORTH_EAST, NORTH_WEST)
        //Single
        singleCanvas()
        block(0..2, 2)

        assertBlockedDirections()

        //Square
        squareCanvas()
        block(1..4, 4)

        assertBlockedDirections()

        //Rectangle
        rectCanvas()
        block(1..4, 6)

        assertBlockedDirections()
    }

    @Test
    fun `Check wall east`() {
        expected(NORTH_EAST, EAST, SOUTH_EAST)
        //Single
        singleCanvas()
        block(2, 0..2)

        assertBlockedDirections()

        //Square
        squareCanvas()
        block(4, 1..4)

        assertBlockedDirections()

        //Rectangle
        rectCanvas()
        block(4, 2..6)

        assertBlockedDirections()
    }

    @Test
    fun `Check wall south`() {
        expected(SOUTH_EAST, SOUTH, SOUTH_WEST)

        //Single
        singleCanvas()
        block(0..2, 0)

        assertBlockedDirections()

        //Square
        squareCanvas()
        block(1..4, 1)

        assertBlockedDirections()

        //Rectangle
        rectCanvas()
        block(1..4, 2)

        assertBlockedDirections()
    }

    @Test
    fun `Check wall west`() {
        expected(SOUTH_WEST, WEST, NORTH_WEST)

        //Single
        singleCanvas()
        block(0, 0..2)

        assertBlockedDirections()

        //Square
        squareCanvas()
        block(1, 1..4)

        assertBlockedDirections()

        //Rectangle
        rectCanvas()
        block(1, 2..6)

        assertBlockedDirections()
    }

    @Test
    fun `Check step off obstacle`() {
        //Single
        singleCanvas()
        block(1, 1)

        assertBlockedDirections()

        //Square
        squareCanvas()
        block(2 until 4, 2 until 4)

        assertBlockedDirections()
    }

    @Test
    fun `Check step off south-west obstacle`() {
        //Square
        squareCanvas()
        block(2, 2)

        assertBlockedDirections()
    }

    @Test
    fun `Check step off north-east obstacle`() {
        //Square
        squareCanvas()
        block(3, 3)

        assertBlockedDirections()
    }

    @Test
    fun `Check step off south-east obstacle`() {
        //Square
        squareCanvas()
        block(3, 2)

        assertBlockedDirections()
    }

    @Test
    fun `Check step off north-west obstacle`() {
        //Square
        squareCanvas()
        block(2, 3)

        assertBlockedDirections()
    }

    @Test
    fun `Check step north-east on rotated canvas`() {
        //TODO shouldn't regionBuilder cover rotation? Might be nice to have an impl to test though
        /*expected(SOUTH_EAST)//NORTH_EAST rotated 90 degrees
        //Single
        singleCanvas()
        block(2, 2)
        rotate(1)
        print()

        assertBlockedDirections()

        //Square
        squareCanvas()
        block(4, 4)
        rotate(1)

        assertBlockedDirections()

        //Rectangle
        rectCanvas()
        block(4, 6)
        rotate(1)

        assertBlockedDirections()*/
    }

    private fun expected(vararg directions: Direction) {
        blocked = arrayOf(*directions)
    }

    private fun assertBlockedDirections(vararg direction: Direction = blocked) {
        val x = start.first
        val y = start.second
        val sizeX = size.first
        val sizeY = size.second

        Direction.all.forEach {
            Assertions.assertThat(traverse!!.traversable(it, x, y, 0, sizeX, sizeY)).describedAs("%s, %s  %s*%s - %s", x, y, sizeX, sizeY, it).isEqualTo(!direction.contains(it))
        }
    }
}