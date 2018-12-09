package worlds.gregs.hestia.game.map

import worlds.gregs.hestia.game.update.Direction

object Flags {

    const val BLOCKED_TILE = 0x1
    const val BRIDGE_TILE = 0x2

    const val FLOOR_BLOCKS_WALK = 0x200000
    const val FLOOR_DECO_BLOCKS_WALK = 0x40000

    const val OBJ = 0x100
    const val OBJ_BLOCKS_FLY = 0x20000
    const val OBJ_BLOCKS_WALK_ALTERNATIVE = 0x40000000

    const val WALL_OBJ_NORTH = 0x2
    const val WALL_OBJ_EAST = 0x8
    const val WALL_OBJ_SOUTH = 0x20
    const val WALL_OBJ_WEST = 0x80

    const val CORNER_OBJ_NORTH_WEST = 0x1
    const val CORNER_OBJ_NORTH_EAST = 0x4
    const val CORNER_OBJ_SOUTH_EAST = 0x10
    const val CORNER_OBJ_SOUTH_WEST = 0x40

    const val WALL_OBJ_NORTH_BLOCKS_FLY = 0x400
    const val WALL_OBJ_EAST_BLOCKS_FLY = 0x1000
    const val WALL_OBJ_SOUTH_BLOCKS_FLY = 0x4000
    const val WALL_OBJ_WEST_BLOCKS_FLY = 0x10000

    const val CORNER_OBJ_NORTH_WEST_BLOCKS_FLY = 0x200
    const val CORNER_OBJ_NORTH_EAST_BLOCKS_FLY = 0x800
    const val CORNER_OBJ_SOUTH_EAST_BLOCKS_FLY = 0x2000
    const val CORNER_OBJ_SOUTH_WEST_BLOCKS_FLY = 0x8000

    const val WALL_OBJ_SOUTH_BLOCKS_WALK_ALTERNATIVE = 0x800000
    const val WALL_OBJ_WEST_BLOCKS_WALK_ALTERNATIVE = 0x2000000
    const val WALL_OBJ_NORTH_BLOCKS_WALK_ALTERNATIVE = 0x8000000
    const val WALL_OBJ_EAST_BLOCKS_WALK_ALTERNATIVE = 0x20000000

    const val CORNER_OBJ_SOUTH_EAST_BLOCKS_WALK_ALTERNATIVE = 0x400000
    const val CORNER_OBJ_SOUTH_WEST_BLOCKS_WALK_ALTERNATIVE = 0x1000000
    const val CORNER_OBJ_NORTH_WEST_BLOCKS_WALK_ALTERNATIVE = 0x4000000
    const val CORNER_OBJ_NORTH_EAST_BLOCKS_WALK_ALTERNATIVE = 0x10000000

    val FLAG_GROUPS = arrayOf(
            arrayOf(
                    intArrayOf(WALL_OBJ_NORTH, WALL_OBJ_EAST, WALL_OBJ_SOUTH, WALL_OBJ_WEST),
                    intArrayOf(CORNER_OBJ_NORTH_WEST, CORNER_OBJ_NORTH_EAST, CORNER_OBJ_SOUTH_EAST, CORNER_OBJ_SOUTH_WEST)
            ),
            arrayOf(
                    intArrayOf(WALL_OBJ_NORTH_BLOCKS_FLY, WALL_OBJ_EAST_BLOCKS_FLY, WALL_OBJ_SOUTH_BLOCKS_FLY, WALL_OBJ_WEST_BLOCKS_FLY),
                    intArrayOf(CORNER_OBJ_NORTH_WEST_BLOCKS_FLY, CORNER_OBJ_NORTH_EAST_BLOCKS_FLY, CORNER_OBJ_SOUTH_EAST_BLOCKS_FLY, CORNER_OBJ_SOUTH_WEST_BLOCKS_FLY)
            ),
            arrayOf(
                    intArrayOf(WALL_OBJ_SOUTH_BLOCKS_WALK_ALTERNATIVE, WALL_OBJ_WEST_BLOCKS_WALK_ALTERNATIVE, WALL_OBJ_NORTH_BLOCKS_WALK_ALTERNATIVE, WALL_OBJ_EAST_BLOCKS_WALK_ALTERNATIVE),
                    intArrayOf(CORNER_OBJ_SOUTH_EAST_BLOCKS_WALK_ALTERNATIVE, CORNER_OBJ_SOUTH_WEST_BLOCKS_WALK_ALTERNATIVE, CORNER_OBJ_NORTH_WEST_BLOCKS_WALK_ALTERNATIVE, CORNER_OBJ_NORTH_EAST_BLOCKS_WALK_ALTERNATIVE)
            )
    )

    val EXTRA_FLAGS = arrayOf(
            intArrayOf(0x82, 0xA, 0x28, 0xA0),
            intArrayOf(0x10400, 0x1400, 0x5000, 0x14000),
            intArrayOf(0x20800000, 0x2800000, 0xa000000, 0x28000000)
    )

    const val BLOCKED = FLOOR_BLOCKS_WALK or FLOOR_DECO_BLOCKS_WALK or OBJ_BLOCKS_WALK_ALTERNATIVE
    const val BLOCKED_WEST = BLOCKED or WALL_OBJ_WEST_BLOCKS_WALK_ALTERNATIVE
    const val BLOCKED_EAST = BLOCKED or WALL_OBJ_EAST_BLOCKS_WALK_ALTERNATIVE
    const val BLOCKED_SOUTH = BLOCKED or WALL_OBJ_SOUTH_BLOCKS_WALK_ALTERNATIVE
    const val BLOCKED_NORTH = BLOCKED or WALL_OBJ_NORTH_BLOCKS_WALK_ALTERNATIVE
    const val BLOCKED_SOUTH_WEST = BLOCKED_SOUTH or WALL_OBJ_WEST_BLOCKS_WALK_ALTERNATIVE or CORNER_OBJ_SOUTH_WEST_BLOCKS_WALK_ALTERNATIVE
    const val BLOCKED_SOUTH_EAST = BLOCKED_SOUTH or WALL_OBJ_EAST_BLOCKS_WALK_ALTERNATIVE or CORNER_OBJ_SOUTH_EAST_BLOCKS_WALK_ALTERNATIVE
    const val BLOCKED_NORTH_WEST = BLOCKED_NORTH or WALL_OBJ_WEST_BLOCKS_WALK_ALTERNATIVE or CORNER_OBJ_NORTH_WEST_BLOCKS_WALK_ALTERNATIVE
    const val BLOCKED_NORTH_EAST = BLOCKED_NORTH or WALL_OBJ_EAST_BLOCKS_WALK_ALTERNATIVE or CORNER_OBJ_NORTH_EAST_BLOCKS_WALK_ALTERNATIVE

    const val CLEAR_WEST = BLOCKED or WALL_OBJ_SOUTH_BLOCKS_WALK_ALTERNATIVE or WALL_OBJ_WEST_BLOCKS_WALK_ALTERNATIVE or WALL_OBJ_NORTH_BLOCKS_WALK_ALTERNATIVE or CORNER_OBJ_SOUTH_WEST_BLOCKS_WALK_ALTERNATIVE or CORNER_OBJ_NORTH_WEST_BLOCKS_WALK_ALTERNATIVE
    const val CLEAR_EAST = BLOCKED or WALL_OBJ_SOUTH_BLOCKS_WALK_ALTERNATIVE or WALL_OBJ_NORTH_BLOCKS_WALK_ALTERNATIVE or WALL_OBJ_EAST_BLOCKS_WALK_ALTERNATIVE or CORNER_OBJ_SOUTH_EAST_BLOCKS_WALK_ALTERNATIVE or CORNER_OBJ_NORTH_EAST_BLOCKS_WALK_ALTERNATIVE
    const val CLEAR_SOUTH = BLOCKED or WALL_OBJ_SOUTH_BLOCKS_WALK_ALTERNATIVE or WALL_OBJ_WEST_BLOCKS_WALK_ALTERNATIVE or WALL_OBJ_EAST_BLOCKS_WALK_ALTERNATIVE or CORNER_OBJ_SOUTH_EAST_BLOCKS_WALK_ALTERNATIVE or CORNER_OBJ_SOUTH_WEST_BLOCKS_WALK_ALTERNATIVE
    const val CLEAR_NORTH = BLOCKED or WALL_OBJ_WEST_BLOCKS_WALK_ALTERNATIVE or WALL_OBJ_NORTH_BLOCKS_WALK_ALTERNATIVE or WALL_OBJ_EAST_BLOCKS_WALK_ALTERNATIVE or CORNER_OBJ_NORTH_WEST_BLOCKS_WALK_ALTERNATIVE or CORNER_OBJ_NORTH_EAST_BLOCKS_WALK_ALTERNATIVE

    @JvmStatic
    fun main2(args: Array<String>) {

        println("${Direction.WEST.getClippingMask()} $BLOCKED_WEST")
        println("${Direction.EAST.getClippingMask()} $BLOCKED_EAST")
        println("${Direction.SOUTH.getClippingMask()} $BLOCKED_SOUTH")
        println("${Direction.NORTH.getClippingMask()} $BLOCKED_NORTH")
        println("${Direction.SOUTH_WEST.getClippingMask()} $BLOCKED_SOUTH_WEST")
        println("${Direction.SOUTH_EAST.getClippingMask()} $BLOCKED_SOUTH_EAST")
        println("${Direction.NORTH_WEST.getClippingMask()} $BLOCKED_NORTH_WEST")
        println("${Direction.NORTH_EAST.getClippingMask()} $BLOCKED_NORTH_EAST")
        println()
        println("${Direction.WEST.getSurroundClippingMask()} $CLEAR_WEST")
        println("${Direction.EAST.getSurroundClippingMask()} $CLEAR_EAST")
        println("${Direction.SOUTH.getSurroundClippingMask()} $CLEAR_SOUTH")
        println("${Direction.NORTH.getSurroundClippingMask()} $CLEAR_NORTH")
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val result = StringBuilder()
        val newLine = System.getProperty("line.separator")

        result.append(this.javaClass.name)
        result.append(" Object {")
        result.append(newLine)

        //determine fields declared in this class only (no fields of superclass)
        val fields = this.javaClass.declaredFields

        //print field names paired with their values
        for (field in fields) {
            result.append("  ")
            try {
                result.append(field.name)
                result.append(": ")
                //requires access to private field:
                val value = field.get(this)
                if(value is Int) {
                    result.append("$value 0x${"%X".format(value)}")
                } else {
                    result.append(field.get(this))
                }
            } catch (ex: IllegalAccessException) {
                ex.printStackTrace()
            }

            result.append(newLine)
        }
        result.append("}")

        println(result.toString())
    }
}