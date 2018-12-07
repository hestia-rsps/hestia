package worlds.gregs.hestia.game.plugins.region.systems.change

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.game.map.Flags
import worlds.gregs.hestia.game.plugins.core.components.map.Position
import worlds.gregs.hestia.game.plugins.region.components.Clipping
import worlds.gregs.hestia.game.plugins.region.components.ClippingMap
import worlds.gregs.hestia.game.plugins.region.components.ProjectileClipping
import worlds.gregs.hestia.game.plugins.region.components.Region
import worlds.gregs.hestia.game.plugins.region.systems.RegionSystem
import worlds.gregs.hestia.game.plugins.region.systems.change.RegionObjectSystem.Companion.isOutOfBounds

class ClippingMaskSystem : PassiveSystem() {

    private lateinit var clippingMapper: ComponentMapper<Clipping>
    private lateinit var projectileClippingMapper: ComponentMapper<ProjectileClipping>
    private lateinit var regionMapper: ComponentMapper<Region>
    private lateinit var regions: RegionSystem

    /**
     * Add, remove or set object mask
     */
    fun changeObject(entityId: Int, map: ClippingMap, plane: Int, x: Int, y: Int, sizeX: Short, sizeY: Short, solid: Boolean, notAlternative: Boolean, changeType: Int) {
        var mask = Flags.OBJ
        if (solid) {
            mask = mask or Flags.OBJ_BLOCKS_FLY
        }
        if (notAlternative) {
            mask = mask or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE
        }
        for (tileX in x until x + sizeX) {
            for (tileY in y until y + sizeY) {
                changeMask(entityId, map, plane, tileX, tileY, mask, changeType)
            }
        }
    }

    /**
     * Add, remove or set wall mask
     */
    fun changeWall(entityId: Int, map: ClippingMap, plane: Int, x: Int, y: Int, type: Int, rotation: Int, solid: Boolean, notAlternative: Boolean, changeType: Int) {
        change(entityId, map, plane, x, y, type, rotation, 0, changeType)
        if(solid) {
            change(entityId, map, plane, x, y, type, rotation, 1, changeType)
        }
        if(notAlternative) {
            change(entityId, map, plane, x, y, type, rotation, 2, changeType)
        }
    }

    /**
     * Add, remove or set regular mask
     */
    private fun change(entityId: Int, map: ClippingMap, plane: Int, x: Int, y: Int, type: Int, rotation: Int, stage: Int, changeType: Int) {
        val offsets = getOffsets(type, rotation)
                ?: return

        val firstFlag = getFlag(type, rotation, stage, true)
        val secondFlag = getFlag(type, rotation, stage, false)

        val extra = getExtra(type, rotation, stage)

        //Type 2 has an extra mask changed
        if (extra != null) {
            changeMask(entityId, map, plane, x, y, extra, changeType)
        }
        //Every type has at least two masked changed
        changeMask(entityId, map, plane, x + offsets[0], y + offsets[1], firstFlag, changeType)
        changeMask(entityId, map, plane, x + offsets[2], y + offsets[3], secondFlag, changeType)
    }

    fun getMasks(map: ClippingMap, plane: Int): Array<IntArray> {
        return map.masks[plane]
    }

    fun getMask(entityId: Int, map: ClippingMap, plane: Int, localX: Int, localY: Int): Int {
        return handleMaskBounds(entityId, map, plane, localX, localY) { plane, x, y ->
            map.masks[plane][x][y]
        }
    }

    /**
     * Apply change to masks
     */
    fun changeMask(entityId: Int, map: ClippingMap, plane: Int, localX: Int, localY: Int, mask: Int, changeType: Int) {
        handleMaskBounds(entityId, map, plane, localX, localY) { plane, x, y ->
            map.masks[plane][x][y] = when (changeType) {
                ADD_MASK -> map.masks[plane][x][y] or mask
                REMOVE_MASK -> map.masks[plane][x][y] and mask.inv()
                SET_MASK -> mask
                else -> map.masks[plane][x][y]
            }
            map.masks[plane][x][y]//Unused
        }
    }

    /**
     * Check that the change is within bounds, if out of bounds recursively apply the change to the correct region
     */
    private fun handleMaskBounds(entityId: Int, map: ClippingMap, plane: Int, localX: Int, localY: Int, action: (Int, Int, Int) -> Int): Int {
        return if (isOutOfBounds(localX, localY)) {
            val region = regionMapper.get(entityId)
            val x = region.x + localX
            val y = region.y + localY
            val regionId = Position.regionId(x, y)
            val newEntityId = regions.getEntity(regionId) ?: return 0
            val newMap = if(map is ProjectileClipping) {
                projectileClippingMapper.create(newEntityId)
            } else {
                clippingMapper.create(newEntityId)
            }
            handleMaskBounds(newEntityId, newMap, plane, x % 64, y % 64, action)
        } else {
            action(plane, localX, localY)
        }
    }

    companion object {

        const val ADD_MASK = 0
        const val REMOVE_MASK = 1
        const val SET_MASK = 2

        private val offsets = arrayOf(
                arrayOf(
                        intArrayOf(0, 0, -1, 0),
                        intArrayOf(0, 0, 0, 1),
                        intArrayOf(0, 0, 1, 0),
                        intArrayOf(0, 0, 0, -1)
                ),
                arrayOf(
                        intArrayOf(0, 0, -1, 1),
                        intArrayOf(0, 0, 1, 1),
                        intArrayOf(0, 0, 1, -1),
                        intArrayOf(0, 0, -1, -1)
                ),
                arrayOf(
                        intArrayOf(-1, 0, 0, 1),
                        intArrayOf(0, 1, 1, 0),
                        intArrayOf(1, 0, 0, -1),
                        intArrayOf(0, -1, -1, 0)
                )
        )

        /**
         * Returns the coordinate offset for a given type & rotation
         * @return [IntArray] offsets in format [x1, y2, x2, y2]
         */
        private fun getOffsets(type: Int, rotation: Int): IntArray? {
            return offsets[if (type == 3) 1 else type][rotation]
        }

        private val indices = arrayOf(
                arrayOf(
                        intArrayOf(3, 0, 1, 2),
                        intArrayOf(1, 2, 3, 0)
                ),
                arrayOf(
                        intArrayOf(0, 1, 2, 3),
                        intArrayOf(2, 3, 0, 1)
                ),
                arrayOf(
                        intArrayOf(1, 2, 3, 0),
                        intArrayOf(2, 3, 0, 1)
                ),
                arrayOf(
                        intArrayOf(0, 1, 2, 3),
                        intArrayOf(2, 3, 0, 1)
                )
        )

        /**
         * Returns the first or second flag to be changed
         * @param type 0-4
         * @param rotation 0-4
         * @param stage Normal, solid or not alternative route 0-2
         * @return [Flags] flag
         */
        private fun getFlag(type: Int, rotation: Int, stage: Int, first: Boolean): Int {
            val id = indices[type][if (first) 0 else 1][rotation]
            return Flags.FLAG_GROUPS[stage][type % 2][id]
        }

        /**
         * Returns the extra flag for type 2
         */
        private fun getExtra(type: Int, rotation: Int, stage: Int): Int? {
            return if(type == 2) {
                Flags.EXTRA_FLAGS[stage][rotation]
            } else {
                null
            }
        }
    }
}