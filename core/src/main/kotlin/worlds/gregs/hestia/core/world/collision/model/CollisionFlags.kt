package worlds.gregs.hestia.core.world.collision.model

import worlds.gregs.hestia.core.display.update.model.Direction
import kotlin.math.pow

object CollisionFlags {

    const val FLOOR = 0x200000
    const val FLOOR_DECO = 0x40000

    const val WALK = 0x100
    const val FLY = WALK shl 9
    const val SWIM = WALK shl 22

    const val BLOCKED = FLOOR or FLOOR_DECO or WALK

    const val WALK_NORTH_OR_WEST = 0x82
    const val WALK_NORTH_OR_EAST = 0xA
    const val WALK_SOUTH_OR_EAST = 0x28
    const val WALK_SOUTH_OR_WEST = 0xA0

    private val flags = mutableMapOf<Direction, Int>()
    private val block = mutableMapOf<Direction, Int>()
    private val clear = mutableMapOf<Direction, Int>()

    fun either(direction: Direction): Int = when (direction) {
        Direction.NORTH_WEST -> WALK_NORTH_OR_WEST
        Direction.NORTH_EAST -> WALK_NORTH_OR_EAST
        Direction.SOUTH_EAST -> WALK_SOUTH_OR_EAST
        Direction.SOUTH_WEST -> WALK_SOUTH_OR_WEST
        else -> 0
    }

    fun Direction.flag(): Int = flags[this] ?: 0
    fun Direction.block(): Int = block[this] ?: 0
    fun Direction.clear(): Int = clear[this] ?: 0
    fun Direction.flag(motionType: Int): Int = applyMotion(flags[this] ?: 0, motionType)
    fun Direction.block(motionType: Int): Int = applyMotion(block[this] ?: 0, motionType)// TODO Test; Potentially won't work due to shifting BLOCKED values?
    fun Direction.clear(motionType: Int): Int = applyMotion(clear[this] ?: 0, motionType)

    fun applyMotion(mask: Int, motion: Int): Int {
        return when (motion) {
            1 -> mask shl 9
            2 -> mask shl 22
            else -> mask
        }
    }

    init {
        Direction.directions.forEach { direction ->
            flags[direction] = if (direction.deltaX == 0 && direction.deltaY == 0) 0 else 2.0.pow(direction.ordinal).toInt()
        }
        Direction.directions.forEach { direction ->
            block[direction] = BLOCKED or when {
                direction.isDiagonal() -> direction.vertical().flag() or direction.horizontal().flag() or direction.flag()
                direction.deltaY != 0 -> direction.vertical().flag()
                direction.deltaX != 0 -> direction.horizontal().flag()
                else -> 0
            }
            clear[direction] = BLOCKED or Direction.directions.filter {
                it != direction && differentOrNone(it.horizontal(), direction.horizontal()) && differentOrNone(it.vertical(), direction.vertical())
            }.fold(0) { acc, v -> acc or v.flag() }
        }
    }

    private fun differentOrNone(first: Direction, second: Direction): Boolean {
        return first != second || (first == Direction.NONE && second == Direction.NONE)
    }
}