package worlds.gregs.hestia.game.plugins.map.systems

import com.artemis.annotations.Wire
import worlds.gregs.hestia.game.api.map.ClippingMasks
import worlds.gregs.hestia.game.api.map.Map
import worlds.gregs.hestia.game.api.map.TileClipping
import worlds.gregs.hestia.game.api.region.Regions
import worlds.gregs.hestia.game.plugins.core.components.map.Chunk.getRotatedChunkRotation
import worlds.gregs.hestia.game.plugins.core.components.map.Chunk.toChunkPosition
import worlds.gregs.hestia.game.plugins.core.components.map.Position
import worlds.gregs.hestia.game.plugins.region.systems.DynamicSystem
import worlds.gregs.hestia.game.update.DirectionUtils

/**
 * TileClipping
 * Checks whether the next tile to step on is free (unclipped)
 */
@Wire(failOnNull = false)
class TileCheckSystem : TileClipping() {
    private var masks: ClippingMasks? = null
    private var map: Map? = null
    private var regions: Regions? = null
    private var dynamic: DynamicSystem? = null

    /**
     * Get the mask of the tile at [x], [y], [plane]
     * @param x The x coordinate of the tile
     * @param y The y coordinate of the tile
     * @param plane The plane coordinate of the tile
     * @return The clipping mask value
     */
    private fun getMask(x: Int, y: Int, plane: Int): Int {
        val regionId = Position.regionId(x, y)
        val entityId = regions?.getEntityId(regionId) ?: return -1
        val clipping = map?.getClipping(entityId) ?: return -1
        return masks?.getMask(entityId, clipping, x % 64, y % 64, plane) ?: -1
    }

    /**
     * Get's the rotation of the chunk, tile [x], [y], [plane] is in.
     * @param x The x coordinate of the tile
     * @param y The y coordinate of the tile
     * @param plane The plane coordinate of the tile
     * @return The chunks rotation
     */
    private fun getRotation(x: Int, y: Int, plane: Int): Int {
        val regionId = Position.regionId(x, y)
        val entityId = regions?.getEntityId(regionId) ?: return 0
        val dynamic = dynamic?.get(entityId) ?: return 0
        val shift = dynamic.regionData[toChunkPosition(x shr 3, y shr 3, plane)] ?: return 0
        return getRotatedChunkRotation(shift)
    }

    /**
     * Checks if a tile is free
     * TODO refactor
     */
    override fun checkWalkStep(plane: Int, x: Int, y: Int, dir: Int, size: Int, sizeY: Int): Boolean {
        var xOffset = DirectionUtils.DELTA_X[dir]
        var yOffset = DirectionUtils.DELTA_Y[dir]
        val rotation = getRotation(x + xOffset, y + yOffset, plane)
        if (rotation != 0) {
            for (rotate in 0 until 4 - rotation) {
                val temp = xOffset
                xOffset = yOffset
                yOffset = 0 - temp
            }
        }

        if (size == 1) {
            val mask = getMask(x + DirectionUtils.DELTA_X[dir], y + DirectionUtils.DELTA_Y[dir], plane)
            if (xOffset == -1 && yOffset == 0) return mask and 0x42240000 == 0
            if (xOffset == 1 && yOffset == 0) return mask and 0x60240000 == 0
            if (xOffset == 0 && yOffset == -1) return mask and 0x40a40000 == 0
            if (xOffset == 0 && yOffset == 1) return mask and 0x48240000 == 0
            if (xOffset == -1 && yOffset == -1) {
                return (mask and 0x43a40000 == 0 && getMask(x - 1, y, plane) and 0x42240000 == 0
                        && getMask(x, y - 1, plane) and 0x40a40000 == 0)
            }
            if (xOffset == 1 && yOffset == -1) {
                return (mask and 0x60e40000 == 0 && getMask(x + 1, y, plane) and 0x60240000 == 0
                        && getMask(x, y - 1, plane) and 0x40a40000 == 0)
            }
            if (xOffset == -1 && yOffset == 1) {
                return (mask and 0x4e240000 == 0 && getMask(x - 1, y, plane) and 0x42240000 == 0
                        && getMask(x, y + 1, plane) and 0x48240000 == 0)
            }
            if (xOffset == 1 && yOffset == 1) {
                return (mask and 0x78240000 == 0 && getMask(x + 1, y, plane) and 0x60240000 == 0
                        && getMask(x, y + 1, plane) and 0x48240000 == 0)
            }
        } else if (size == 2) {
            if (xOffset == -1 && yOffset == 0)
                return getMask(x - 1, y, plane) and 0x43a40000 == 0 && getMask(x - 1, y + 1, plane) and 0x4e240000 == 0
            if (xOffset == 1 && yOffset == 0)
                return getMask(x + 2, y, plane) and 0x60e40000 == 0 && getMask(x + 2, y + 1, plane) and 0x78240000 == 0
            if (xOffset == 0 && yOffset == -1)
                return getMask(x, y - 1, plane) and 0x43a40000 == 0 && getMask(x + 1, y - 1, plane) and 0x60e40000 == 0
            if (xOffset == 0 && yOffset == 1)
                return getMask(x, y + 2, plane) and 0x4e240000 == 0 && getMask(x + 1, y + 2, plane) and 0x78240000 == 0
            if (xOffset == -1 && yOffset == -1)
                return (getMask(x - 1, y, plane) and 0x4fa40000 == 0 && getMask(x - 1, y - 1, plane) and 0x43a40000 == 0
                        && getMask(x, y - 1, plane) and 0x63e40000 == 0)
            if (xOffset == 1 && yOffset == -1)
                return (getMask(x + 1, y - 1, plane) and 0x63e40000 == 0
                        && getMask(x + 2, y - 1, plane) and 0x60e40000 == 0
                        && getMask(x + 2, y, plane) and 0x78e40000 == 0)
            if (xOffset == -1 && yOffset == 1)
                return (getMask(x - 1, y + 1, plane) and 0x4fa40000 == 0
                        && getMask(x - 1, y + 1, plane) and 0x4e240000 == 0
                        && getMask(x, y + 2, plane) and 0x7e240000 == 0)
            if (xOffset == 1 && yOffset == 1)
                return (getMask(x + 1, y + 2, plane) and 0x7e240000 == 0
                        && getMask(x + 2, y + 2, plane) and 0x78240000 == 0
                        && getMask(x + 1, y + 1, plane) and 0x78e40000 == 0)
        } else {
            if (xOffset == -1 && yOffset == 0) {
                if (getMask(x - 1, y, plane) and 0x43a40000 != 0 || getMask(x - 1, -1 + (y + size), plane) and 0x4e240000 != 0)
                    return false
                for (sizeOffset in 1 until size - 1)
                    if (getMask(x - 1, y + sizeOffset, plane) and 0x4fa40000 != 0) return false
            } else if (xOffset == 1 && yOffset == 0) {
                if (getMask(x + size, y, plane) and 0x60e40000 != 0 || getMask(x + size, (y - (-size + 1)), plane) and 0x78240000 != 0)
                    return false
                for (sizeOffset in 1 until size - 1)
                    if (getMask(x + size, y + sizeOffset, plane) and 0x78e40000 != 0) return false
            } else if (xOffset == 0 && yOffset == -1) {
                if (getMask(x, y - 1, plane) and 0x43a40000 != 0 || getMask(x + size - 1, y - 1, plane) and 0x60e40000 != 0)
                    return false
                for (sizeOffset in 1 until size - 1)
                    if (getMask(x + sizeOffset, y - 1, plane) and 0x63e40000 != 0) return false
            } else if (xOffset == 0 && yOffset == 1) {
                if (getMask(x, y + size, plane) and 0x4e240000 != 0 || getMask(x + (size - 1), y + size, plane) and 0x78240000 != 0)
                    return false
                for (sizeOffset in 1 until size - 1)
                    if (getMask(x + sizeOffset, y + size, plane) and 0x7e240000 != 0) return false
            } else if (xOffset == -1 && yOffset == -1) {
                if (getMask(x - 1, y - 1, plane) and 0x43a40000 != 0) return false
                for (sizeOffset in 1 until size)
                    if (getMask(x - 1, y + (-1 + sizeOffset), plane) and 0x4fa40000 != 0 || getMask(sizeOffset - 1 + x, y - 1, plane) and 0x63e40000 != 0)
                        return false
            } else if (xOffset == 1 && yOffset == -1) {
                if (getMask(x + size, y - 1, plane) and 0x60e40000 != 0) return false
                for (sizeOffset in 1 until size)
                    if (getMask(x + size, sizeOffset + (-1 + y), plane) and 0x78e40000 != 0 || getMask(x + sizeOffset, y - 1, plane) and 0x63e40000 != 0)
                        return false
            } else if (xOffset == -1 && yOffset == 1) {
                if (getMask(x - 1, y + size, plane) and 0x4e240000 != 0) return false
                for (sizeOffset in 1 until size)
                    if (getMask(x - 1, y + sizeOffset, plane) and 0x4fa40000 != 0 || getMask(-1 + (x + sizeOffset), y + size, plane) and 0x7e240000 != 0)
                        return false
            } else if (xOffset == 1 && yOffset == 1) {
                if (getMask(x + size, y + size, plane) and 0x78240000 != 0) return false
                for (sizeOffset in 1 until size)
                    if (getMask(x + sizeOffset, y + size, plane) and 0x7e240000 != 0 || getMask(x + size, y + sizeOffset, plane) and 0x78e40000 != 0)
                        return false
            }
        }
        return true
    }
}