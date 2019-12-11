package worlds.gregs.hestia.core.world.map.systems

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import worlds.gregs.hestia.game.entity.components.Position
import worlds.gregs.hestia.api.map.Clipping
import worlds.gregs.hestia.api.map.ClippingMasks
import worlds.gregs.hestia.api.map.Map
import worlds.gregs.hestia.api.region.Regions
import worlds.gregs.hestia.game.map.Flags
import worlds.gregs.hestia.game.map.MapConstants.isOutOfBounds
import worlds.gregs.hestia.core.world.map.components.ProjectileClipping
import worlds.gregs.hestia.core.world.region.components.RegionIdentifier

@Wire(failOnNull = false)
class ClippingMaskSystem : ClippingMasks() {

    private var map: Map? = null
    private var regions: Regions? = null
    private lateinit var regionMapper: ComponentMapper<RegionIdentifier>

    /*
        Objects
     */

    override fun addObject(entityId: Int, localX: Int, localY: Int, plane: Int, sizeX: Short, sizeY: Short, solid: Boolean, notAlternative: Boolean) {
        changeObject(entityId, localX, localY, plane, sizeX, sizeY, solid, notAlternative, ADD_MASK)
    }

    override fun removeObject(entityId: Int, localX: Int, localY: Int, plane: Int, sizeX: Short, sizeY: Short, solid: Boolean, notAlternative: Boolean) {
        changeObject(entityId, localX, localY, plane, sizeX, sizeY, solid, notAlternative, REMOVE_MASK)
    }

    override fun setObject(entityId: Int, localX: Int, localY: Int, plane: Int, sizeX: Short, sizeY: Short, solid: Boolean, notAlternative: Boolean) {
        changeObject(entityId, localX, localY, plane, sizeX, sizeY, solid, notAlternative, SET_MASK)
    }

    override fun changeObject(entityId: Int, localX: Int, localY: Int, plane: Int, sizeX: Short, sizeY: Short, solid: Boolean, notAlternative: Boolean, changeType: Int) {
        changeObject(entityId, getClipping(entityId, false), localX, localY, plane, sizeX, sizeY, solid, notAlternative, changeType)
        if (solid) {
            changeObject(entityId, getClipping(entityId, true), localX, localY, plane, sizeX, sizeY, solid, notAlternative, changeType)
        }
    }

    /**
     * Applies [changeType] changes to clipping mask for an object
     * @param entityId The region entity id
     * @param clipping The clipping to apply the changes too
     * @param localX The local x coordinate of the object
     * @param localY The local y coordinate of the object
     * @param plane The plane coordinate of the object
     * @param sizeX The width of the object
     * @param sizeY The height of the object
     * @param solid Whether the object is solid
     * @param notAlternative Whether the objects clipping can be ignored on an alternative route
     * @param changeType How to change the clipping mask
     */
    private fun changeObject(entityId: Int, clipping: Clipping?, localX: Int, localY: Int, plane: Int, sizeX: Short, sizeY: Short, solid: Boolean, notAlternative: Boolean, changeType: Int) {
        if(clipping == null) {
            return
        }
        var mask = Flags.OBJ
        if (solid) {
            mask = mask or Flags.OBJ_BLOCKS_FLY
        }
        if (notAlternative) {
            mask = mask or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE
        }
        for (tileX in localX until localX + sizeX) {
            for (tileY in localY until localY + sizeY) {
                changeMask(entityId, clipping, tileX, tileY, plane, mask, changeType)
            }
        }
    }

    /*
        Walls
     */

    override fun addWall(entityId: Int, localX: Int, localY: Int, plane: Int, type: Int, rotation: Int, solid: Boolean, notAlternative: Boolean) {
        changeWall(entityId, localX, localY, plane, type, rotation, solid, notAlternative, ADD_MASK)
    }

    override fun removeWall(entityId: Int, localX: Int, localY: Int, plane: Int, type: Int, rotation: Int, solid: Boolean, notAlternative: Boolean) {
        changeWall(entityId, localX, localY, plane, type, rotation, solid, notAlternative, REMOVE_MASK)
    }

    override fun setWall(entityId: Int, localX: Int, localY: Int, plane: Int, type: Int, rotation: Int, solid: Boolean, notAlternative: Boolean) {
        changeWall(entityId, localX, localY, plane, type, rotation, solid, notAlternative, SET_MASK)
    }

    override fun changeWall(entityId: Int, localX: Int, localY: Int, plane: Int, type: Int, rotation: Int, solid: Boolean, notAlternative: Boolean, changeType: Int) {
        changeWall(entityId, getClipping(entityId, false), localX, localY, plane, type, rotation, solid, notAlternative, changeType)
        if (solid) {
            changeWall(entityId, getClipping(entityId, false), localX, localY, plane, type, rotation, solid, notAlternative, changeType)
        }
    }

    /**
     * Applies [changeType] changes to clipping mask for a wall
     * @param entityId The region entity id
     * @param clipping The clipping to apply the changes too
     * @param localX The local x coordinate of the wall
     * @param localY The local y coordinate of the wall
     * @param plane The plane coordinate of the wall
     * @param type The wall type
     * @param rotation The rotation of the wall
     * @param solid Whether the object is solid
     * @param notAlternative Whether the objects clipping can be ignored on an alternative route
     * @param changeType How to change the clipping mask
     */
    private fun changeWall(entityId: Int, clipping: Clipping?, localX: Int, localY: Int, plane: Int, type: Int, rotation: Int, solid: Boolean, notAlternative: Boolean, changeType: Int) {
        if(clipping == null) {
            return
        }
        changeWall(entityId, clipping, localX, localY, plane, type, rotation, 0, changeType)
        if (solid) {
            changeWall(entityId, clipping, localX, localY, plane, type, rotation, 1, changeType)
        }
        if (notAlternative) {
            changeWall(entityId, clipping, localX, localY, plane, type, rotation, 2, changeType)
        }
    }

    /**
     * Applies [changeType] changes to clipping mask for a wall
     * @param entityId The region entity id
     * @param clipping The clipping to apply the changes too
     * @param localX The local x coordinate of the wall
     * @param localY The local y coordinate of the wall
     * @param plane The plane coordinate of the wall
     * @param type The wall type
     * @param rotation The rotation of the wall
     * @param stage Which stage of the mask is being changes (0 normal, 1 projectile/flying, 2 alternative)
     * @param changeType How to change the clipping mask
     */
    private fun changeWall(entityId: Int, clipping: Clipping, localX: Int, localY: Int, plane: Int, type: Int, rotation: Int, stage: Int, changeType: Int) {
        val offsets = getOffsets(type, rotation)
                ?: return

        val firstFlag = getFlag(type, rotation, stage, true)
        val secondFlag = getFlag(type, rotation, stage, false)

        val extra = getExtra(type, rotation, stage)

        //Type 2 has an extra mask changed
        if (extra != null) {
            changeMask(entityId, clipping, localX, localY, plane, extra, changeType)
        }
        //Every type has at least two masked changed
        changeMask(entityId, clipping, localX + offsets[0], localY + offsets[1], plane, firstFlag, changeType)
        changeMask(entityId, clipping, localX + offsets[2], localY + offsets[3], plane, secondFlag, changeType)
    }

    /*
        Masks
     */

    override fun addMask(entityId: Int, localX: Int, localY: Int, plane: Int, mask: Int) {
        changeMask(entityId, localX, localY, plane, mask, ADD_MASK)
    }

    override fun removeMask(entityId: Int, localX: Int, localY: Int, plane: Int, mask: Int) {
        changeMask(entityId, localX, localY, plane, mask, REMOVE_MASK)
    }

    override fun setMask(entityId: Int, localX: Int, localY: Int, plane: Int, mask: Int) {
        changeMask(entityId, localX, localY, plane, mask, SET_MASK)
    }

    override fun changeMask(entityId: Int, localX: Int, localY: Int, plane: Int, mask: Int, changeType: Int) {
        changeMask(entityId, getClipping(entityId, false), localX, localY, plane, mask, changeType)
    }

    /**
     * Applies [changeType] changes to a tile's clipping mask
     * @param entityId The region entity id
     * @param clipping The clipping to apply the changes too
     * @param localX The x coordinate of the wall
     * @param localY The y coordinate of the wall
     * @param plane The plane coordinate of the wall
     * @param mask The mask to apply
     * @param changeType How to change the clipping mask
     */
    private fun changeMask(entityId: Int, clipping: Clipping?, localX: Int, localY: Int, plane: Int, mask: Int, changeType: Int) {
        if(clipping == null) {
            return
        }
        handleMaskBounds(entityId, clipping, localX, localY, plane) { x, y, plane ->
            when (changeType) {
                ADD_MASK -> clipping.addMask(x, y, plane, mask)
                REMOVE_MASK -> clipping.removeMask(x, y, plane, mask)
                SET_MASK -> clipping.setMask(x, y, plane, mask)
            }
            null
        }
    }


    override tailrec fun getMask(entityId: Int, clipping: Clipping, localX: Int, localY: Int, plane: Int): Int {
        //We're using duplicate of handleMaskBounds here to avoid the high-order function performance hit
        return if (isOutOfBounds(localX, localY)) {
            val region = regionMapper.get(entityId)
            val offsetX = if(localX > 64) 1 else if(localX < 0) -1 else 0
            val offsetY = if(localY > 64) 1 else if(localY < 0) -1 else 0
            val newEntityId = regions?.getEntityId(((region.regionX + offsetX) shl 8) + (region.regionY + offsetY)) ?: return 0
            val newClipping = getClipping(newEntityId, clipping is ProjectileClipping) ?: return 0
            getMask(newEntityId, newClipping, (localX - offsetX * 64) % 64, (localY - offsetY * 64) % 64, plane)
        } else {
            clipping.getMask(localX, localY, plane)
        }
    }

    /**
     * Returns clipping of region
     * @param entityId Regions entity id
     * @param projectile Whether to return the projectile map or regular map
     * @return Clipping map
     */
    private fun getClipping(entityId: Int, projectile: Boolean): Clipping? {
        return if (projectile) {
            map?.createProjectileMap(entityId)
        } else {
            map?.createClipping(entityId)
        }
    }

    /**
     * If mask is out of region bounds (e.g. a large object overlapping the region edge)
     * Apply the [action] to the correct region clipping
     * @param entityId The original regions entity id
     * @param clipping The original clipping to apply the [action] to
     * @param localX The x coordinate of the tile
     * @param localY The y coordinate of the tile
     * @param plane The plane coordinate of the tile
     * @param action The action to take when the coordinates are within bounds
     * @return Mask value required for [getMask], nullable as it's a workaround of needing to be recursive and inline.
     */
    private tailrec fun handleMaskBounds(entityId: Int, clipping: Clipping?, localX: Int, localY: Int, plane: Int, action: (Int, Int, Int) -> Int?) : Int? {
        return if (isOutOfBounds(localX, localY)) {
            val region = regionMapper.get(entityId)
            val x = region.x + localX
            val y = region.y + localY
            val regionId = Position.regionId(x, y)
            val newEntityId = regions?.getEntityId(regionId) ?: return null
            val newClipping = getClipping(newEntityId, clipping is ProjectileClipping)
            handleMaskBounds(newEntityId, newClipping, x % 64, y % 64, plane, action)
        } else {
            action(localX, localY, plane)
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
            return if (type == 2) {
                Flags.EXTRA_FLAGS[stage][rotation]
            } else {
                null
            }
        }
    }
}