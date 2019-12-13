package worlds.gregs.hestia.core.display.update.model

import worlds.gregs.hestia.core.world.collision.model.Flags
import worlds.gregs.hestia.core.world.collision.model.Flags.BLOCKED
import worlds.gregs.hestia.core.world.collision.model.Flags.BLOCKED_NORTH_EAST
import worlds.gregs.hestia.core.world.collision.model.Flags.BLOCKED_NORTH_WEST
import worlds.gregs.hestia.core.world.collision.model.Flags.BLOCKED_SOUTH_EAST
import worlds.gregs.hestia.core.world.collision.model.Flags.BLOCKED_SOUTH_WEST
import worlds.gregs.hestia.core.world.collision.model.Flags.CLEAR_EAST
import worlds.gregs.hestia.core.world.collision.model.Flags.CLEAR_NORTH
import worlds.gregs.hestia.core.world.collision.model.Flags.CLEAR_SOUTH
import worlds.gregs.hestia.core.world.collision.model.Flags.CLEAR_WEST
import worlds.gregs.hestia.core.world.collision.model.Flags.CORNER_OBJ_NORTH_EAST_BLOCKS_WALK_ALTERNATIVE
import worlds.gregs.hestia.core.world.collision.model.Flags.CORNER_OBJ_NORTH_WEST_BLOCKS_WALK_ALTERNATIVE
import worlds.gregs.hestia.core.world.collision.model.Flags.CORNER_OBJ_SOUTH_EAST_BLOCKS_WALK_ALTERNATIVE
import worlds.gregs.hestia.core.world.collision.model.Flags.CORNER_OBJ_SOUTH_WEST_BLOCKS_WALK_ALTERNATIVE
import worlds.gregs.hestia.core.world.collision.model.Flags.WALL_OBJ_EAST_BLOCKS_WALK_ALTERNATIVE
import worlds.gregs.hestia.core.world.collision.model.Flags.WALL_OBJ_NORTH_BLOCKS_WALK_ALTERNATIVE
import worlds.gregs.hestia.core.world.collision.model.Flags.WALL_OBJ_SOUTH_BLOCKS_WALK_ALTERNATIVE
import worlds.gregs.hestia.core.world.collision.model.Flags.WALL_OBJ_WEST_BLOCKS_WALK_ALTERNATIVE

enum class Direction(val deltaX: Int, val deltaY: Int, val value: Int, val mask: Int) {
    NORTH_WEST(-1, 1, 0, CORNER_OBJ_NORTH_WEST_BLOCKS_WALK_ALTERNATIVE),
    NORTH(0, 1, 1, WALL_OBJ_NORTH_BLOCKS_WALK_ALTERNATIVE),
    NORTH_EAST(1, 1, 2, CORNER_OBJ_NORTH_EAST_BLOCKS_WALK_ALTERNATIVE),
    WEST(-1, 0, 3, WALL_OBJ_WEST_BLOCKS_WALK_ALTERNATIVE),
    EAST(1, 0, 4, WALL_OBJ_EAST_BLOCKS_WALK_ALTERNATIVE),
    SOUTH_WEST(-1, -1, 5, CORNER_OBJ_SOUTH_WEST_BLOCKS_WALK_ALTERNATIVE),
    SOUTH(0, -1, 6, WALL_OBJ_SOUTH_BLOCKS_WALK_ALTERNATIVE),
    SOUTH_EAST(1, -1, 7, CORNER_OBJ_SOUTH_EAST_BLOCKS_WALK_ALTERNATIVE),
    NONE(0, 0, -1, -1);

    fun isDiagonal(): Boolean {
        return deltaX != 0 && deltaY != 0
    }

    fun getClippingMask(): Int {
        return BLOCKED or diagonalValue() or verticalValue() or horizontalValue()
    }

    fun getVerticalMask(): Int {
        return BLOCKED or verticalValue()
    }

    fun getHorizontalMask(): Int {
        return BLOCKED or horizontalValue()
    }

    fun verticalValue(): Int {
        return when (deltaY) {
            1 -> WALL_OBJ_NORTH_BLOCKS_WALK_ALTERNATIVE
            -1 -> WALL_OBJ_SOUTH_BLOCKS_WALK_ALTERNATIVE
            else -> 0
        }
    }

    fun horizontalValue(): Int {
        return when (deltaX) {
            1 -> WALL_OBJ_EAST_BLOCKS_WALK_ALTERNATIVE
            -1 -> WALL_OBJ_WEST_BLOCKS_WALK_ALTERNATIVE
            else -> 0
        }
    }

    fun diagonalValue(): Int {
        return if (isDiagonal()) mask else 0
    }

    fun getSurroundClippingMask(): Int {
        return when (deltaY) {
            1 -> CLEAR_NORTH
            -1 -> CLEAR_SOUTH
            else -> when (deltaX) {
                1 -> CLEAR_EAST
                -1 -> CLEAR_WEST
                else -> BLOCKED
            }
        }
    }

    fun getSouthCornerClippingMask(): Int {
        return when (this) {
            WEST -> BLOCKED_SOUTH_WEST
            EAST -> BLOCKED_SOUTH_EAST
            SOUTH -> BLOCKED_SOUTH_WEST
            NORTH -> BLOCKED_NORTH_WEST
            else -> 0
        }
    }
    fun getNorthCornerClippingMask(): Int {
        return when (this) {
            WEST -> BLOCKED_NORTH_WEST
            EAST -> BLOCKED_NORTH_EAST
            SOUTH -> BLOCKED_SOUTH_EAST
            NORTH -> BLOCKED_NORTH_EAST
            else -> 0
        }
    }

    fun getHorizontalClear(): Int {
        return when (deltaX) {
            1 -> Flags.CLEAR_EAST
            -1 -> Flags.CLEAR_WEST
            else -> 0
        }
    }

    fun getVerticalClear(): Int {
        return when (deltaY) {
            1 -> Flags.CLEAR_NORTH
            -1 -> Flags.CLEAR_SOUTH
            else -> 0
        }
    }

    fun vertical(): Direction? {
        return when (deltaY) {
            1 -> NORTH
            -1 -> SOUTH
            else -> null
        }
    }

    fun horizontal(): Direction? {
        return when (deltaX) {
            1 -> EAST
            -1 -> WEST
            else -> null
        }
    }

    fun diagonal(): Direction? {
        return if (isDiagonal()) this else null
    }

    fun reverse(): Direction {
        return fromDirection(7 - ordinal)
                ?: NONE
    }

    companion object {
        val size = values().size

        val all = listOf(WEST, EAST, SOUTH, NORTH, SOUTH_WEST, SOUTH_EAST, NORTH_WEST, NORTH_EAST)

        fun fromDirection(direction: Int): Direction? {
            return values().firstOrNull { it.value == direction }
        }

        fun fromDelta(deltaX: Int, deltaY: Int): Direction? {
            return values().firstOrNull { it.deltaX == deltaX && it.deltaY == deltaY }
        }

        fun getDirection(deltaX: Int, deltaY: Int): Int {
            return fromDelta(deltaX, deltaY)?.value ?: -1
        }

        fun deltaX(direction: Int): Int {
            return values().firstOrNull { it.value == direction }?.deltaX ?: 0
        }

        fun deltaY(direction: Int): Int {
            return values().firstOrNull { it.value == direction }?.deltaY ?: 0
        }
    }
}