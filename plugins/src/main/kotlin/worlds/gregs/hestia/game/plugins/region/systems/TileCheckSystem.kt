package worlds.gregs.hestia.game.plugins.region.systems

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.game.plugins.core.components.map.Position
import worlds.gregs.hestia.game.plugins.region.components.Clipping
import worlds.gregs.hestia.game.plugins.region.components.Rotation
import worlds.gregs.hestia.game.update.DirectionUtils

class TileCheckSystem : PassiveSystem() {
    private lateinit var regions: RegionSystem
    private lateinit var rms: RegionMapSystem
    private lateinit var clippingMapper: ComponentMapper<Clipping>
    private lateinit var rotationMapper: ComponentMapper<Rotation>

    private fun getMask(plane: Int, x: Int, y: Int): Int {
        val regionId = Position.regionId(x, y)
        val entityId = regions.getEntity(regionId) ?: return -1
        if(!clippingMapper.has(entityId)) {
            return -1
        }
        val clippingMap = clippingMapper.get(entityId)
        return rms.getMask(entityId, clippingMap, plane, x % 64, y % 64)
    }

    private fun getRotation(plane: Int, x: Int, y: Int): Int {
        val regionId = Position.regionId(x, y)
        return 0//regions.getRotation(plane, x % 64, y % 64)
    }


    fun checkWalkStep(plane: Int, x: Int, y: Int, dir: Int, size: Int): Boolean {
        var xOffset = DirectionUtils.DELTA_X[dir]
        var yOffset = DirectionUtils.DELTA_Y[dir]
        val rotation = getRotation(plane, x + xOffset, y + yOffset)
        if (rotation != 0) {
            for (rotate in 0 until 4 - rotation) {
                val temp = xOffset
                xOffset = yOffset
                yOffset = 0 - temp
            }
        }

        if (size == 1) {
            val mask = getMask(plane, x + DirectionUtils.DELTA_X[dir], y + DirectionUtils.DELTA_Y[dir])
            if (xOffset == -1 && yOffset == 0) return mask and 0x42240000 == 0
            if (xOffset == 1 && yOffset == 0) return mask and 0x60240000 == 0
            if (xOffset == 0 && yOffset == -1) return mask and 0x40a40000 == 0
            if (xOffset == 0 && yOffset == 1) return mask and 0x48240000 == 0
            if (xOffset == -1 && yOffset == -1) {
                return (mask and 0x43a40000 == 0 && getMask(plane, x - 1, y) and 0x42240000 == 0
                        && getMask(plane, x, y - 1) and 0x40a40000 == 0)
            }
            if (xOffset == 1 && yOffset == -1) {
                return (mask and 0x60e40000 == 0 && getMask(plane, x + 1, y) and 0x60240000 == 0
                        && getMask(plane, x, y - 1) and 0x40a40000 == 0)
            }
            if (xOffset == -1 && yOffset == 1) {
                return (mask and 0x4e240000 == 0 && getMask(plane, x - 1, y) and 0x42240000 == 0
                        && getMask(plane, x, y + 1) and 0x48240000 == 0)
            }
            if (xOffset == 1 && yOffset == 1) {
                return (mask and 0x78240000 == 0 && getMask(plane, x + 1, y) and 0x60240000 == 0
                        && getMask(plane, x, y + 1) and 0x48240000 == 0)
            }
        } else if (size == 2) {
            if (xOffset == -1 && yOffset == 0)
                return getMask(plane, x - 1, y) and 0x43a40000 == 0 && getMask(plane, x - 1, y + 1) and 0x4e240000 == 0
            if (xOffset == 1 && yOffset == 0)
                return getMask(plane, x + 2, y) and 0x60e40000 == 0 && getMask(plane, x + 2, y + 1) and 0x78240000 == 0
            if (xOffset == 0 && yOffset == -1)
                return getMask(plane, x, y - 1) and 0x43a40000 == 0 && getMask(plane, x + 1, y - 1) and 0x60e40000 == 0
            if (xOffset == 0 && yOffset == 1)
                return getMask(plane, x, y + 2) and 0x4e240000 == 0 && getMask(plane, x + 1, y + 2) and 0x78240000 == 0
            if (xOffset == -1 && yOffset == -1)
                return (getMask(plane, x - 1, y) and 0x4fa40000 == 0 && getMask(plane, x - 1, y - 1) and 0x43a40000 == 0
                        && getMask(plane, x, y - 1) and 0x63e40000 == 0)
            if (xOffset == 1 && yOffset == -1)
                return (getMask(plane, x + 1, y - 1) and 0x63e40000 == 0
                        && getMask(plane, x + 2, y - 1) and 0x60e40000 == 0
                        && getMask(plane, x + 2, y) and 0x78e40000 == 0)
            if (xOffset == -1 && yOffset == 1)
                return (getMask(plane, x - 1, y + 1) and 0x4fa40000 == 0
                        && getMask(plane, x - 1, y + 1) and 0x4e240000 == 0
                        && getMask(plane, x, y + 2) and 0x7e240000 == 0)
            if (xOffset == 1 && yOffset == 1)
                return (getMask(plane, x + 1, y + 2) and 0x7e240000 == 0
                        && getMask(plane, x + 2, y + 2) and 0x78240000 == 0
                        && getMask(plane, x + 1, y + 1) and 0x78e40000 == 0)
        } else {
            if (xOffset == -1 && yOffset == 0) {
                if (getMask(plane, x - 1, y) and 0x43a40000 != 0 || getMask(plane, x - 1, -1 + (y + size)) and 0x4e240000 != 0)
                    return false
                for (sizeOffset in 1 until size - 1)
                    if (getMask(plane, x - 1, y + sizeOffset) and 0x4fa40000 != 0) return false
            } else if (xOffset == 1 && yOffset == 0) {
                if (getMask(plane, x + size, y) and 0x60e40000 != 0 || getMask(plane, x + size, (y - (-size + 1))) and 0x78240000 != 0)
                    return false
                for (sizeOffset in 1 until size - 1)
                    if (getMask(plane, x + size, y + sizeOffset) and 0x78e40000 != 0) return false
            } else if (xOffset == 0 && yOffset == -1) {
                if (getMask(plane, x, y - 1) and 0x43a40000 != 0 || getMask(plane, x + size - 1, y - 1) and 0x60e40000 != 0)
                    return false
                for (sizeOffset in 1 until size - 1)
                    if (getMask(plane, x + sizeOffset, y - 1) and 0x63e40000 != 0) return false
            } else if (xOffset == 0 && yOffset == 1) {
                if (getMask(plane, x, y + size) and 0x4e240000 != 0 || getMask(plane, x + (size - 1), y + size) and 0x78240000 != 0)
                    return false
                for (sizeOffset in 1 until size - 1)
                    if (getMask(plane, x + sizeOffset, y + size) and 0x7e240000 != 0) return false
            } else if (xOffset == -1 && yOffset == -1) {
                if (getMask(plane, x - 1, y - 1) and 0x43a40000 != 0) return false
                for (sizeOffset in 1 until size)
                    if (getMask(plane, x - 1, y + (-1 + sizeOffset)) and 0x4fa40000 != 0 || getMask(plane, sizeOffset - 1 + x, y - 1) and 0x63e40000 != 0)
                        return false
            } else if (xOffset == 1 && yOffset == -1) {
                if (getMask(plane, x + size, y - 1) and 0x60e40000 != 0) return false
                for (sizeOffset in 1 until size)
                    if (getMask(plane, x + size, sizeOffset + (-1 + y)) and 0x78e40000 != 0 || getMask(plane, x + sizeOffset, y - 1) and 0x63e40000 != 0)
                        return false
            } else if (xOffset == -1 && yOffset == 1) {
                if (getMask(plane, x - 1, y + size) and 0x4e240000 != 0) return false
                for (sizeOffset in 1 until size)
                    if (getMask(plane, x - 1, y + sizeOffset) and 0x4fa40000 != 0 || getMask(plane, -1 + (x + sizeOffset), y + size) and 0x7e240000 != 0)
                        return false
            } else if (xOffset == 1 && yOffset == 1) {
                if (getMask(plane, x + size, y + size) and 0x78240000 != 0) return false
                for (sizeOffset in 1 until size)
                    if (getMask(plane, x + sizeOffset, y + size) and 0x7e240000 != 0 || getMask(plane, x + size, y + sizeOffset) and 0x78e40000 != 0)
                        return false
            }
        }
        return true
    }
}