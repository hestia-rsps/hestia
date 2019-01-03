package worlds.gregs.hestia.game.update

import worlds.gregs.hestia.game.update.Direction.Companion.fromDelta
import worlds.gregs.hestia.game.update.Direction.Companion.fromDirection

class DirectionUtils {
    companion object {

        fun getOffset(current: Int, destination: Int): Int {
            return if (current < destination) 1 else if (current > destination) -1 else 0
        }

        fun getMoveDirection(nextX: Int, nextY: Int, lastX: Int, lastY: Int): Direction? {
            //Calculate the delta offsets then return direction value
            return fromDelta(getOffset(lastX, nextX), getOffset(lastY, nextY))
        }
        fun getFaceDirection(xOffset: Int, yOffset: Int): Int {
            return (Math.atan2(xOffset * -1.0, yOffset * -1.0) * 2607.5945876176133).toInt() and 0x3fff
        }

        fun getPlayerWalkingDirection(dx: Int, dy: Int): Int {
            val dir = fromDelta(dx, -dy) ?: Direction.NONE
            return dir.value
        }

        @JvmStatic
        fun main(args: Array<String>) {
            RUN_X.forEachIndexed { index, x ->
                println("$x ${RUN_Y[index]} ${getPlayerRunningDirection(x, RUN_Y[index])} ${fromDirection(getPlayerRunningDirection(x, RUN_Y[index]))?.deltaX} ${fromDirection(getPlayerRunningDirection(x, RUN_Y[index]))?.deltaY}")
            }

            println("${TEST_X.map { -it }.reversed().union(TEST_X.toList())}")
            println("${TEST_Y.map { -it }.reversed()} ${TEST_Y.toList()}")

        }

        private val RUN_X = intArrayOf(-2, -1, 0, 1, 2, -2, 2, -2, 2, -2, 2, -2, -1, 0, 1, 2)
        private val RUN_Y = intArrayOf(-2, -2, -2, -2, -2, -1, -1, 0, 0, 1, 1, 2, 2, 2, 2, 2)

        private val TEST_X = intArrayOf(2, -2, 2, -2, -1, 0, 1, 2)
        private val TEST_Y = intArrayOf(0, 1, 1, 2, 2, 2, 2, 2)

        fun getPlayerRunningDirection(dx: Int, dy: Int): Int {
            RUN_X.forEachIndexed { i, x ->
                if (dx == x && dy == RUN_Y[i]) {
                    return i
                }
            }
            return -1
        }

        private val MOVE_X = intArrayOf(0, 1, 1, 1, 0, -1, -1, -1)
        private val MOVE_Y = intArrayOf(1, 1, 0, -1, -1, -1, 0, 1)

        /*@JvmStatic
        fun main(args: Array<String>) {
            MOVE_X.forEachIndexed { index, x ->
                println("$x ${MOVE_Y[index]} ${getMobMoveDirection(x, MOVE_Y[index])}")
            }
        }*/

        fun getMobMoveDirection(direction: Direction): Int {
            return if (direction == Direction.NONE) {
                -1
            } else {
                getMobMoveDirection(direction.deltaX, direction.deltaY)
            }
        }
        private fun getMobMoveDirection(dx: Int, dy: Int): Int {
            MOVE_X.forEachIndexed { i, x ->
                if (close(x, dx) && close(MOVE_Y[i], dy)) {
                    return i
                }
            }
            return -1
        }

        private fun close(type: Int, value: Int): Boolean {
            return when (type) {
                -1 -> value < 0
                1 -> value > 0
                else -> value == 0
            }
        }
    }
}