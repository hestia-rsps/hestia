package worlds.gregs.hestia.game.plugins.core.components.map

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
open class Position() : Component() {

    constructor(x: Int, y: Int, plane: Int = 0) : this() {
        this.x = x
        this.y = y
        this.plane = plane
    }

    var x: Int = 0
    var y: Int = 0
    var plane: Int = 0

    val chunkX: Int
        get() = x shr 3

    val chunkY: Int
        get() = y shr 3

    val xInChunk: Int
        get() = x and 0x7

    val yInChunk: Int
        get() = y and 0x7

    /* Regions */
    val regionId: Int
        get() = (regionX shl 8) + regionY

    val regionX: Int
        get() = x shr 6

    val regionY: Int
        get() = y shr 6

    val xInRegion: Int
        get() = x and 0x3F

    val yInRegion: Int
        get() = y and 0x3F

    override fun toString(): String {
        return "$x, $y, $plane"
    }

    fun withinDistance(from: Position, distance: Int = 15): Boolean {
        return plane == from.plane && Math.abs(x - from.x) <= distance && Math.abs(y - from.y) <= distance
    }

    fun getDistance(from: Position): Int {
        return Math.max(Math.abs(x - from.x), Math.abs(y - from.y))
    }

    fun getDirectDistance(other: Position): Int {
        val deltaX = x - other.x
        val deltaY = y - other.y
        return Math.ceil(Math.sqrt((deltaX * deltaX + deltaY * deltaY).toDouble())).toInt()
    }

    fun set(position: Position) {
        set(position.x, position.y, position.plane)
    }

    fun set(x: Int, y: Int, plane: Int = 0) {
        this.x = x
        this.y = y
        this.plane = plane
    }

    val locationHash18Bit: Int
        get() = regionY + (regionX shl 8) + (plane shl 16)

    val locationHash30Bit: Int
        get() = y + (x shl 14) + (plane shl 28)


    companion object {
        val EMPTY = Position()

        fun create(x: Int, y: Int, z: Int): Position {
            return Position(x, y, z)
        }

        fun from(hash: Int): Position {
            return create(hash and 0xff72 shr 8, 0xff and hash, hash shr 16)
        }

        fun delta(from: Position, to: Position): Position {
            return create(from.x - to.x, from.y - to.y, from.plane - to.plane)
        }

        inline fun delta(from: Position, to: Position, action: (Int, Int, Int) -> Unit) {
            action(from.x - to.x, from.y - to.y, from.plane - to.plane)
        }

        inline fun regionDelta(from: Position, to: Position, action: (Int, Int, Int) -> Unit) {
            action(from.regionX - to.regionX, from.regionY - to.regionY, from.plane - to.plane)
        }

        fun clone(position: Position): Position {
            return create(position.x, position.y, position.plane)
        }

        fun regionId(x: Int, y: Int): Int {
            return (x shr 6 shl 8) + (y shr 6)
        }
    }
}