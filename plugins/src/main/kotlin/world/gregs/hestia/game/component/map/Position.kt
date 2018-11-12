package world.gregs.hestia.game.component.map

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Position : Component() {
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

    companion object {
        val EMPTY = Position()
        fun create(x: Int, y: Int, z: Int): Position {
            val pos = Position()
            pos.x = x
            pos.y = y
            pos.plane = z
            return pos
        }

        fun from(hash: Int): Position {
            return create(hash and 0xff72 shr 8, 0xff and hash, hash shr 16)
        }

        fun delta(from: Position, to: Position): Position {
            return create(from.x - to.x, from.y - to.y, from.plane - to.plane)
        }

        fun clone(position: Position): Position {
            return create(position.x, position.y, position.plane)
        }

        fun withinDistance(from: Position, to: Position): Boolean {
            return if (to.plane != from.plane) {
                false
            } else {
                Math.abs(to.x - from.x) <= 15 && Math.abs(to.y - from.y) <= 15
            }
        }

        fun withinDistance(from: Position, to: Position, distance: Int): Boolean {
            if (to.plane != from.plane){
                return false
            }
            val deltaX = to.x - from.x
            val deltaY = to.y - from.y
            return deltaX <= distance && deltaX >= -distance && deltaY <= distance && deltaY >= -distance
        }
    }
}