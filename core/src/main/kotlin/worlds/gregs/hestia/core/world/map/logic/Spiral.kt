package worlds.gregs.hestia.core.world.map.logic

import java.util.function.BiPredicate

object Spiral {
    /**
     * Handles outward rectangle areas
     * @param x The points bottom left x position
     * @param y The points bottom left y position
     * @param pointWidth The width of the point
     * @param pointHeight The height of the point
     * @param radius The radius to spiral
     * @param predicate The delta coordinates, return false to stop early
     */
    @Suppress("NAME_SHADOWING")
    fun outwardsRect(x: Int, y: Int, pointWidth: Int, pointHeight: Int, xBounds: IntRange, yBounds: IntRange, radius: Int, predicate: BiPredicate<Int, Int>) {
        var x = x
        var y = y
        var direction = 1
        var steps = 1
        var count = 0
        //Loop until we've covered every point in the grid
        val area = (pointWidth + radius * 2) * (pointHeight + radius * 2)
        while (count < area) {
            //Repeats each step twice e.g.
            //1, 1, 2, 2, 3, 3, 4, 4, 5, 5...
            repeat(2) {
                repeat(steps) {
                    //If in bounds
                    if (x in xBounds && y in yBounds) {
                        count++
                        //Apply predicate (stop if reached max)
                        if (!predicate.test(x, y) || count >= area) {
                            return
                        }
                    }
                    //Move one step in the direction
                    when (direction) {
                        0 -> y--
                        1 -> x--
                        2 -> y++
                        3 -> x++
                    }
                }
                //Turn direction
                direction = (direction + 1) % 4
            }
            steps++
        }
    }

    /**
     * Loop coordinates for each point expanding counter clockwise in a spiral
     * Order e.g.
     * [2][3][4]
     * [1][0][5]
     * [8][7][6]
     * @param x The points bottom left x position
     * @param y The points bottom left y position
     * @param pointWidth The width of the point
     * @param pointHeight The height of the point
     * @param radius The radius to spiral
     * @param predicate The delta coordinates, return false to stop early
     */
    fun outwards(x: Int, y: Int, pointWidth: Int, pointHeight: Int, radius: Int, predicate: BiPredicate<Int, Int>) {
        if(pointWidth != pointHeight) {
            outwardsRect(x, y, pointWidth, pointHeight, x - radius until x + pointWidth + radius, y - radius until y + pointHeight + radius, radius, predicate)
        } else {
            outwardsSquare(x, y, pointWidth, pointHeight, radius, predicate)
        }
    }

    /**
     * Handles only square areas
     * @param x The points bottom left x position
     * @param y The points bottom left y position
     * @param pointWidth The width of the point
     * @param pointHeight The height of the point
     * @param radius The radius to spiral
     * @param predicate The delta coordinates, return false to stop early
     */
    @Suppress("NAME_SHADOWING")
    private fun outwardsSquare(x: Int, y: Int, pointWidth: Int, pointHeight: Int, radius: Int, predicate: BiPredicate<Int, Int>) {
        var x = x
        var y = y
        var direction = 1
        var steps = 1
        var count = 0
        //Loop until we've covered every point in the grid
        val area = (pointWidth + radius * 2) * (pointHeight + radius * 2)
        while (count < area) {
            //Repeats each step twice e.g.
            //1, 1, 2, 2, 3, 3, 4, 4, 5, 5...
            repeat(2) {
                repeat(steps) {
                    //If in bounds
                        count++
                        //Apply predicate (stop if reached max)
                        if (!predicate.test(x, y) || count >= area) {
                            return
                        }
                    //Move one step in the direction
                    when (direction) {
                        0 -> y--
                        1 -> x--
                        2 -> y++
                        3 -> x++
                    }
                }
                //Turn direction
                direction = (direction + 1) % 4
            }
            steps++
        }
    }

    /**
     * Loop coordinates for each point of a spiral from outside in
     * @param targetX The finish x coordinate
     * @param targetY The finish y coordinate
     * @param radius The radius of the spiral around the target
     */
    fun inwards(targetX: Int, targetY: Int, radius: Int, predicate: BiPredicate<Int, Int>) {
        val size = 1 + radius * 2
        var value = 1
        var minCol = targetY - radius
        var maxCol = targetY + radius
        var minRow = targetX - radius
        var maxRow = targetX + radius

        while (value <= size * size) {
            for (i in minRow..maxRow) {
                if (!predicate.test(i, minCol)) {
                    return
                }
                value++
            }
            for (i in minCol + 1..maxCol) {
                if (!predicate.test(maxRow, i)) {
                    return
                }
                value++
            }
            for (i in maxRow - 1 downTo minRow) {
                if (!predicate.test(i, maxCol)) {
                    return
                }
                value++
            }
            for (i in maxCol - 1 downTo minCol + 1) {
                if (!predicate.test(minRow, i)) {
                    return
                }
                value++
            }
            minCol++
            minRow++
            maxCol--
            maxRow--
        }
    }

    private val cache = HashMap<Int, Array<Pair<Int, Int>>>()

    fun spiral(radius: Int): Array<Pair<Int, Int>> {
        return cache.getOrPut(radius) {
            val list = ArrayList<Pair<Int, Int>>()
            outwards(0, 0, 1, 1, radius, BiPredicate { x, y ->
                list.add(Pair(x, y))
                true
            })
            list.toTypedArray()
        }
    }
}