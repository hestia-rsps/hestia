package worlds.gregs.hestia.core.display.update.logic

import worlds.gregs.hestia.core.display.update.model.Direction
import worlds.gregs.hestia.core.display.update.model.Direction.Companion.fromDelta
import kotlin.math.atan2

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
            return (atan2(xOffset * -1.0, yOffset * -1.0) * 2607.5945876176133).toInt() and 0x3fff
        }

        fun getPlayerWalkingDirection(dx: Int, dy: Int): Int {
            val dir = fromDelta(dx, -dy) ?: Direction.NONE
            return dir.value
        }

        private val RUN_X = intArrayOf(-2, -1, 0, 1, 2, -2, 2, -2, 2, -2, 2, -2, -1, 0, 1, 2)
        private val RUN_Y = intArrayOf(-2, -2, -2, -2, -2, -1, -1, 0, 0, 1, 1, 2, 2, 2, 2, 2)

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

        fun getNpcMoveDirection(direction: Direction): Int {
            return if (direction == Direction.NONE) {
                -1
            } else {
                getNpcMoveDirection(direction.deltaX, direction.deltaY)
            }
        }
        private fun getNpcMoveDirection(dx: Int, dy: Int): Int {
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