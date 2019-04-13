package worlds.gregs.hestia.game.plugins.movement.systems.calc

import com.artemis.BaseSystem
import com.artemis.Entity
import org.assertj.core.api.Assertions.assertThat
import worlds.gregs.hestia.game.entity.components.Position
import worlds.gregs.hestia.game.entity.components.Size
import worlds.gregs.hestia.api.movement.Mobile
import worlds.gregs.hestia.game.plugins.movement.components.Steps
import worlds.gregs.hestia.game.client.update.block.Direction
import worlds.gregs.hestia.services.getComponent
import worlds.gregs.hestia.services.getSystem

internal abstract class PathTester(offset: Boolean, vararg systems: BaseSystem) : MovementTester(offset, *systems) {

    fun assertPath(expected: List<IntArray>? = null, display: Boolean = false, action: (Entity) -> Unit) {
        val x = start.first
        val y = start.second
        val sizeX = size.first
        val sizeY = size.second

        check(x, y, sizeX, sizeY, expected, display, action)
    }

    private fun mockEntity(x: Int, y: Int, width: Int, height: Int): Entity {
        val entity = world.createEntity()
        entity.edit().add(Position(x, y)).add(Mobile()).add(Steps())
        if (width != 1 || height != 1) {
            entity.edit().add(Size(width, height))
        }
        tick()
        return entity
    }

    private fun check(startX: Int, startY: Int, width: Int, height: Int, expected: List<IntArray>? = null, display: Boolean = false, action: (Entity) -> Unit) {
        //Create
        val entity = mockEntity(startX, startY, width, height)
        val clipping = world.getSystem(MovementTester.ClippingBuilderTester::class)

        clip?.load(Position.create(startX, startY, 0), false)

        //Apply change
        action(entity)
        tick()

        //Get steps
        val steps = entity.getComponent(Steps::class)!!

        //Check if successful
        if (!steps.hasNext) {
            throw NoPath()
        }

        //Clone
        val directions = ArrayList<Direction>()
        while (steps.hasNext) {
            directions.add(steps.nextDirection)
        }

        //Check assertions with expected results
        if (expected != null) {
            assertPath(startX, startY, directions, expected)
        }

        //Print path
        if (display) {
            printPath(startX, startY, directions)
            println()

            val array = clipping.createDisplay(false)
            println("Path")
            applyPath(array, startX, startY, directions)
            applyEntity(array, startX, startY, width, height)
            applyLast(array, steps.lastX!!, steps.lastY!!)
            printClip(array)
        }
    }

    private fun assertPath(startX: Int, startY: Int, dirs: List<Direction>, expected: List<IntArray>) {
        assertThat(dirs.size).isEqualTo(expected.size)
        path(startX, startY, dirs) { x, y, index, _ ->
            assertThat(index < expected.size).isTrue()
            assertThat(x).isEqualTo(expected[index][0])
            assertThat(y).isEqualTo(expected[index][1])
        }
    }

    private fun printPath(startX: Int, startY: Int, dirs: List<Direction>) {
        path(startX, startY, dirs) { x, y, index, dir ->
            println("Step $index: [$x, $y] Dir: $dir")
        }
    }

    private fun path(startX: Int, startY: Int, dirs: List<Direction>, action: (Int, Int, Int, Direction) -> Unit) {
        var x = startX
        var y = startY
        dirs.forEachIndexed { index, dir ->
            x += dir.deltaX
            y += dir.deltaY
            action(x, y, index, dir)
        }
    }

    private fun applyPath(array: Array<CharArray>, startX: Int, startY: Int, path: List<Direction>) {
        var x = startX
        var y = startY
        //Apply path
        path.forEach { dir ->
            x += dir.deltaX
            y += dir.deltaY
            apply(array, x, y, when (dir.value) {
                0, 7 -> '\\'
                1, 6 -> '|'
                2, 5 -> '/'
                3, 4 -> '-'
                else -> ' '
            })
        }
    }

    private fun applyEntity(array: Array<CharArray>, startX: Int, startY: Int, width: Int, height: Int) {
        for(x in startX until startX + width) {
            for(y in startY until startY + height) {
                apply(array, x, y, 'E')
            }
        }
    }

    private fun applyLast(array: Array<CharArray>, lastX: Int, lastY: Int) {
        apply(array, lastX, lastY, 'X')
    }

    private fun apply(array: Array<CharArray>, x: Int, y: Int, char: Char) {
        if(x >= array.size || x < 0) {
            throw IndexOutOfBoundsException("$x ${array.size}")
        }

        if(y >= array[x].size || y < 0) {
            throw IndexOutOfBoundsException("$y ${array[x].size}")
        }

        array[x][y] = char
    }

    internal class NoPath : Exception()
}