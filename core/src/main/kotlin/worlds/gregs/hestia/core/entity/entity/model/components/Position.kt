package worlds.gregs.hestia.core.entity.entity.model.components

import com.artemis.Component
import com.artemis.annotations.DelayedComponentRemoval
import worlds.gregs.hestia.core.display.update.logic.sync.ViewportSystem.Companion.DEFAULT_VIEW_DISTANCE
import worlds.gregs.hestia.core.world.map.model.MapConstants.MAP_SIZES
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.sqrt

@DelayedComponentRemoval
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

    fun same(position: Position): Boolean {
        return x == position.x && y == position.y && plane == position.plane
    }

    fun same(x: Int, y: Int, plane: Int): Boolean {
        return this.x == x && this.y == y && this.plane == plane
    }

    fun withinDistance(from: Position, distance: Int = DEFAULT_VIEW_DISTANCE): Boolean {
        return plane == from.plane && withinRange(x, y, from.x, from.y, distance)
    }

    fun getDistance(from: Position): Int {
        return abs(x - from.x).coerceAtLeast(abs(y - from.y))
    }

    fun getDirectDistance(other: Position): Int {
        val deltaX = x - other.x
        val deltaY = y - other.y
        return ceil(sqrt((deltaX * deltaX + deltaY * deltaY).toDouble())).toInt()
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
        get() = hash18Bit(regionX, regionY, plane)

    val locationHash30Bit: Int
        get() = hash24Bit(x, y, plane)

    companion object {
        val EMPTY = Position()

        fun localPosition(coordinate: Int, chunk: Int, mapSize: Int = 0) =
                coordinate - 8 * (chunk - (MAP_SIZES[mapSize] shr 4))

        fun withinRange(x: Int, y: Int, x2: Int, y2: Int, radius: Int = DEFAULT_VIEW_DISTANCE): Boolean {
            return abs(x - x2) <= radius && abs(y - y2) <= radius
        }

        fun distance(x: Int, y: Int, x2: Int, y2: Int): Int {
            return abs(x - x2).coerceAtLeast(abs(y - y2))
        }

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

        fun hash18Bit(x: Int, y: Int, plane: Int): Int {
            return y + (x shl 8) + (plane shl 16)
        }

        fun hash24Bit(x: Int, y: Int, plane: Int = 0): Int {
            return y + (x shl 14) + (plane shl 28)
        }

        fun regionId(x: Int, y: Int): Int {
            return (x shr 6 shl 8) + (y shr 6)
        }
    }
}