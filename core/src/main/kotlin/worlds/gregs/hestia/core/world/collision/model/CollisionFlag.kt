package worlds.gregs.hestia.core.world.collision.model

import worlds.gregs.hestia.core.display.update.model.Direction
import kotlin.math.pow

object CollisionFlag {

    const val FLOOR = 0x200000
    const val FLOOR_DECO = 0x40000
    const val WALL = 0x80000

    const val WALK = 0x100
    const val FLY = WALK shl 9
    const val SWIM = WALK shl 22

    const val BLOCKED = FLOOR or FLOOR_DECO or WALK

    const val WALK_NORTH_OR_WEST = 0x82
    const val WALK_NORTH_OR_EAST = 0xA
    const val WALK_SOUTH_OR_EAST = 0x28
    const val WALK_SOUTH_OR_WEST = 0xA0

    const val WALKING = 0
    const val FLYING = 1
    const val SWIMMING = 2

    private val flags = mutableMapOf<Direction, Int>()
    private val block = mutableMapOf<Direction, IntArray>()
    private val clear = mutableMapOf<Direction, IntArray>()
    private val wall = mutableMapOf<Direction, IntArray>()

    fun either(direction: Direction): Int = when (direction) {
        Direction.NORTH_WEST -> WALK_NORTH_OR_WEST
        Direction.NORTH_EAST -> WALK_NORTH_OR_EAST
        Direction.SOUTH_EAST -> WALK_SOUTH_OR_EAST
        Direction.SOUTH_WEST -> WALK_SOUTH_OR_WEST
        else -> 0
    }

    fun Direction.flag(): Int = flags[this] ?: 0
    fun Direction.block(): Int = block(WALKING)
    fun Direction.clear(): Int = clear(WALKING)
    fun Direction.wall(): Int = clear(WALKING)
    fun Direction.flag(motionType: Int): Int = applyMotion(flag(), motionType)
    fun Direction.block(motionType: Int): Int = block[this]?.getOrNull(motionType) ?: 0
    fun Direction.clear(motionType: Int): Int = clear[this]?.getOrNull(motionType) ?: 0
    fun Direction.wall(motionType: Int): Int = clear[this]?.getOrNull(motionType) ?: 0

    private fun applyMotion(flag: Int, motion: Int): Int {
        return when (motion) {
            FLYING -> flag shl 9
            SWIMMING -> flag shl 22
            else -> flag
        }
    }

    init {
        Direction.directions.forEach { direction ->
            flags[direction] = if (direction.deltaX == 0 && direction.deltaY == 0) 0 else 2.0.pow(direction.ordinal).toInt()
        }
        Direction.directions.forEach { direction ->
            val blockFlag = when {
                direction.isDiagonal() -> direction.vertical().flag() or direction.horizontal().flag() or direction.flag()
                direction.deltaY != 0 -> direction.vertical().flag()
                direction.deltaX != 0 -> direction.horizontal().flag()
                else -> 0
            }
            val clearFlag = Direction.directions.filter {
                it != direction && differentOrNone(it.horizontal(), direction.horizontal()) && differentOrNone(it.vertical(), direction.vertical())
            }.fold(0) { acc, v -> acc or v.flag() }

            block[direction] = motionArray(blockFlag)
            clear[direction] = motionArray(clearFlag)
            wall[direction] = motionArray(blockFlag or WALL)
        }
    }

    private fun motionArray(flag: Int) : IntArray = intArrayOf(
        BLOCKED or applyMotion(flag, WALKING),
        BLOCKED or applyMotion(flag, FLYING),
        BLOCKED or applyMotion(flag, SWIMMING)
    )

    private fun differentOrNone(first: Direction, second: Direction): Boolean {
        return first != second || (first == Direction.NONE && second == Direction.NONE)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        Direction.directions.forEach {
            println("$it 0x${"%X".format(it.flag())} ${it.flag()}")
        }
    }
}