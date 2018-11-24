package worlds.gregs.hestia.game.update

class DirectionUtils {
    companion object {

    val REGION_MOVEMENT = arrayOf(
            intArrayOf(0, 3, 5),
            intArrayOf(1, -1, 6),
            intArrayOf(2, 4, 7)
    )

    val DELTA_X = intArrayOf(-1, 0, 1, -1, 1, -1, 0, 1)
    val DELTA_Y = intArrayOf(1, 1, 1, 0, 0, -1, -1, -1)

    fun getOffset(current: Int, destination: Int): Int {
        return if (current < destination) 1 else if (current > destination) -1 else 0
    }

    fun getMoveDirection(xOffset: Int, yOffset: Int): Int {
        val x = getOffset(0, xOffset)
        val y = getOffset(yOffset, 0)

        return REGION_MOVEMENT[x + 1][y + 1]
    }

    fun getFaceDirection(xOffset: Int, yOffset: Int): Int {
        return (Math.atan2(xOffset * -1.0, yOffset * -1.0) * 2607.5945876176133).toInt() and 0x3fff
    }

    fun getMobMoveDirection(direction: Int): Int {
        return if (direction < 0) {
            -1
        } else {
            getMobMoveDirection(DELTA_X[direction], DELTA_Y[direction])
        }
    }

    fun getPlayerWalkingDirection(dx: Int, dy: Int): Int {
        if (dx == -1 && dy == -1) {
            return 0
        }
        if (dx == 0 && dy == -1) {
            return 1
        }
        if (dx == 1 && dy == -1) {
            return 2
        }
        if (dx == -1 && dy == 0) {
            return 3
        }
        if (dx == 1 && dy == 0) {
            return 4
        }
        if (dx == -1 && dy == 1) {
            return 5
        }
        if (dx == 0 && dy == 1) {
            return 6
        }
        return if (dx == 1 && dy == 1) {
            7
        } else -1
    }

    fun getPlayerRunningDirection(dx: Int, dy: Int): Int {
        if (dx == -2 && dy == -2) {
            return 0
        }
        if (dx == -1 && dy == -2) {
            return 1
        }
        if (dx == 0 && dy == -2) {
            return 2
        }
        if (dx == 1 && dy == -2) {
            return 3
        }
        if (dx == 2 && dy == -2) {
            return 4
        }
        if (dx == -2 && dy == -1) {
            return 5
        }
        if (dx == 2 && dy == -1) {
            return 6
        }
        if (dx == -2 && dy == 0) {
            return 7
        }
        if (dx == 2 && dy == 0) {
            return 8
        }
        if (dx == -2 && dy == 1) {
            return 9
        }
        if (dx == 2 && dy == 1) {
            return 10
        }
        if (dx == -2 && dy == 2) {
            return 11
        }
        if (dx == -1 && dy == 2) {
            return 12
        }
        if (dx == 0 && dy == 2) {
            return 13
        }
        if (dx == 1 && dy == 2) {
            return 14
        }
        return if (dx == 2 && dy == 2) {
            15
        } else {
            -1
        }
    }

    private fun getMobMoveDirection(dx: Int, dy: Int): Int {
        if (dx == 0 && dy > 0) {
            return 0
        }
        if (dx > 0 && dy > 0) {
            return 1
        }
        if (dx > 0 && dy == 0) {
            return 2
        }
        if (dx > 0 && dy < 0) {
            return 3
        }
        if (dx == 0 && dy < 0) {
            return 4
        }
        if (dx < 0 && dy < 0) {
            return 5
        }
        if (dx < 0 && dy == 0) {
            return 6
        }
        return if (dx < 0 && dy > 0) {
            7
        } else {
            -1
        }
    }
    }
}