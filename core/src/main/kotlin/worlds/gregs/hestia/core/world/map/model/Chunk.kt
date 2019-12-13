package worlds.gregs.hestia.core.world.map.model

import worlds.gregs.hestia.core.entity.entity.model.components.Position

object Chunk {

    fun toChunkPosition(position: Position): Int {
        return toChunkPosition(position.chunkX, position.chunkY, position.plane)
    }

    fun toChunkPosition(chunkX: Int, chunkY: Int, plane: Int): Int {
        return chunkY + (chunkX shl 14) + (plane shl 28)
    }

    fun getChunkX(shift: Int): Int {
        return shift shr 14 and 0x3fff
    }

    fun getChunkY(shift: Int): Int {
        return shift and 0x3fff
    }

    fun getChunkPlane(shift: Int): Int {
        return shift shr 28
    }

    /*
        Rotated chunk position shifts
     */

    fun toRotatedChunkPosition(chunkX: Int, chunkY: Int, plane: Int, rotation: Int): Int {
        return rotation shl 1 or (plane shl 24) or (chunkX shl 14) or (chunkY shl 3)
    }
    fun getRotatedChunkX(shift: Int): Int {
        return (shift and 0xffe240) shr 14
    }

    fun getRotatedChunkY(shift: Int): Int {
        return (shift and 0x3ff9) shr 3
    }

    fun getRotatedChunkPlane(shift: Int): Int {
        return (shift and 0x3e548f0) shr 24
    }

    fun getRotatedChunkRotation(shift: Int): Int {
        return (shift shr 1) and 0x3
    }
}