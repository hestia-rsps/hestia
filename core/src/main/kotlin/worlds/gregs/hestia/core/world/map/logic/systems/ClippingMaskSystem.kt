package worlds.gregs.hestia.core.world.map.logic.systems

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import worlds.gregs.hestia.core.display.update.model.Direction.Companion.cardinal
import worlds.gregs.hestia.core.display.update.model.Direction.Companion.ordinal
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.world.collision.model.CollisionFlags
import worlds.gregs.hestia.core.world.collision.model.CollisionFlags.either
import worlds.gregs.hestia.core.world.collision.model.CollisionFlags.flag
import worlds.gregs.hestia.core.world.map.api.Clipping
import worlds.gregs.hestia.core.world.map.api.ClippingMasks
import worlds.gregs.hestia.core.world.map.api.Map
import worlds.gregs.hestia.core.world.map.model.MapConstants.isOutOfBounds
import worlds.gregs.hestia.core.world.region.api.Regions
import worlds.gregs.hestia.core.world.region.model.components.RegionIdentifier

@Wire(failOnNull = false)
class ClippingMaskSystem : ClippingMasks() {

    private var map: Map? = null
    private var regions: Regions? = null
    private lateinit var regionMapper: ComponentMapper<RegionIdentifier>

    /*
        Objects
     */

    override fun addObject(entityId: Int, localX: Int, localY: Int, plane: Int, sizeX: Short, sizeY: Short, sky: Boolean, sea: Boolean) {
        changeObject(entityId, localX, localY, plane, sizeX, sizeY, sky, sea, ADD_MASK)
    }

    override fun removeObject(entityId: Int, localX: Int, localY: Int, plane: Int, sizeX: Short, sizeY: Short, sky: Boolean, sea: Boolean) {
        changeObject(entityId, localX, localY, plane, sizeX, sizeY, sky, sea, REMOVE_MASK)
    }

    override fun setObject(entityId: Int, localX: Int, localY: Int, plane: Int, sizeX: Short, sizeY: Short, sky: Boolean, sea: Boolean) {
        changeObject(entityId, localX, localY, plane, sizeX, sizeY, sky, sea, SET_MASK)
    }

    override fun changeObject(entityId: Int, localX: Int, localY: Int, plane: Int, sizeX: Short, sizeY: Short, sky: Boolean, sea: Boolean, changeType: Int) {
        changeObject(entityId, getClipping(entityId), localX, localY, plane, sizeX, sizeY, sky, sea, changeType)
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
     * @param sky Whether the object blocks flying entities
     * @param sea Whether the object blocks swimming entities
     * @param changeType How to change the clipping mask
     */
    private fun changeObject(entityId: Int, clipping: Clipping?, localX: Int, localY: Int, plane: Int, sizeX: Short, sizeY: Short, sky: Boolean, sea: Boolean, changeType: Int) {
        if (clipping == null) {
            return
        }
        var mask = CollisionFlags.WALK
        if (sky) {
            mask = mask or CollisionFlags.FLY
        }
        if (sea) {
            mask = mask or CollisionFlags.SWIM
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

    override fun addWall(entityId: Int, localX: Int, localY: Int, plane: Int, type: Int, rotation: Int, sky: Boolean, sea: Boolean) {
        changeWall(entityId, localX, localY, plane, type, rotation, sky, sea, ADD_MASK)
    }

    override fun removeWall(entityId: Int, localX: Int, localY: Int, plane: Int, type: Int, rotation: Int, sky: Boolean, sea: Boolean) {
        changeWall(entityId, localX, localY, plane, type, rotation, sky, sea, REMOVE_MASK)
    }

    override fun setWall(entityId: Int, localX: Int, localY: Int, plane: Int, type: Int, rotation: Int, sky: Boolean, sea: Boolean) {
        changeWall(entityId, localX, localY, plane, type, rotation, sky, sea, SET_MASK)
    }

    override fun changeWall(entityId: Int, localX: Int, localY: Int, plane: Int, type: Int, rotation: Int, sky: Boolean, sea: Boolean, changeType: Int) {
        changeWall(entityId, getClipping(entityId), localX, localY, plane, type, rotation, sky, sea, changeType)
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
     * @param sky Whether the object blocks flying entities
     * @param sea Whether the object blocks swimming entities
     * @param changeType How to change the clipping mask
     */
    private fun changeWall(entityId: Int, clipping: Clipping?, localX: Int, localY: Int, plane: Int, type: Int, rotation: Int, sky: Boolean, sea: Boolean, changeType: Int) {
        if (clipping == null) {
            return
        }
        changeWall(entityId, clipping, localX, localY, plane, type, rotation, 0, changeType)
        if (sky) {
            changeWall(entityId, clipping, localX, localY, plane, type, rotation, 1, changeType)
        }
        if (sea) {
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
     * @param motion Which stage of the mask is being changes (0 normal, 1 projectile/flying, 2 alternative)
     * @param changeType How to change the clipping mask
     */
    private fun changeWall(entityId: Int, clipping: Clipping, localX: Int, localY: Int, plane: Int, type: Int, rotation: Int, motion: Int, changeType: Int) {
        if (type == 2) {
            val direction = ordinal[rotation and 0x3]
            changeMask(entityId, clipping, localX, localY, plane, either(direction), changeType)
            changeMask(entityId, clipping, localX + direction.deltaX, localY, plane, direction.horizontal().flag(), changeType)
            changeMask(entityId, clipping, localX, localY + direction.deltaY, plane, direction.vertical().flag(), changeType)
        } else {
            val direction = when (type) {
                0 -> cardinal[(rotation + 3) and 0x3]
                1, 3 -> ordinal[rotation and 0x3]
                else -> return
            }
            changeMask(entityId, clipping, localX, localY, plane, direction.flag(motion), changeType)
            changeMask(entityId, clipping, localX + direction.deltaX, localY + direction.deltaY, plane, direction.inverse().flag(motion), changeType)
        }
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
        changeMask(entityId, getClipping(entityId), localX, localY, plane, mask, changeType)
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
        if (clipping == null) {
            return
        }
        handleMaskBounds(entityId, localX, localY, plane) { x, y, plane ->
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
        return if (isOutOfBounds(localX, localY)) {//TODO remove this, a local coordinate should be able to be out of bounds.
            val region = regionMapper.get(entityId)
            val offsetX = if (localX > 64) 1 else if (localX < 0) -1 else 0
            val offsetY = if (localY > 64) 1 else if (localY < 0) -1 else 0
            val newEntityId = regions?.getEntityId(((region.regionX + offsetX) shl 8) + (region.regionY + offsetY))
                    ?: return 0
            val newClipping = getClipping(newEntityId) ?: return 0
            getMask(newEntityId, newClipping, (localX - offsetX * 64) % 64, (localY - offsetY * 64) % 64, plane)
        } else {
            clipping.getMask(localX, localY, plane)
        }
    }

    /**
     * Returns clipping of region
     * @param entityId Regions entity id
     * @return Clipping map
     */
    private fun getClipping(entityId: Int): Clipping? {
        return map?.createClipping(entityId)
    }

    /**
     * If mask is out of region bounds (e.g. a large object overlapping the region edge)
     * Apply the [action] to the correct region clipping
     * @param entityId The original regions entity id
     * @param localX The x coordinate of the tile
     * @param localY The y coordinate of the tile
     * @param plane The plane coordinate of the tile
     * @param action The action to take when the coordinates are within bounds
     * @return Mask value required for [getMask], nullable as it's a workaround of needing to be recursive and inline.
     */
    private tailrec fun handleMaskBounds(entityId: Int, localX: Int, localY: Int, plane: Int, action: (Int, Int, Int) -> Int?): Int? {
        return if (isOutOfBounds(localX, localY)) {
            val region = regionMapper.get(entityId)
            val x = region.x + localX
            val y = region.y + localY
            val regionId = Position.regionId(x, y)
            val newEntityId = regions?.getEntityId(regionId) ?: return null
            handleMaskBounds(newEntityId, x % 64, y % 64, plane, action)
        } else {
            action(localX, localY, plane)
        }
    }

    companion object {
        const val ADD_MASK = 0
        const val REMOVE_MASK = 1
        const val SET_MASK = 2
    }
}