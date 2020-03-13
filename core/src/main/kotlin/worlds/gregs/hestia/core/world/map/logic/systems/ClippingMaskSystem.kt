package worlds.gregs.hestia.core.world.map.logic.systems

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
import worlds.gregs.hestia.core.world.region.api.Regions

@Wire(failOnNull = false)
class ClippingMaskSystem : ClippingMasks() {

    private var map: Map? = null
    private var regions: Regions? = null

    override fun changeObject(x: Int, y: Int, plane: Int, sizeX: Short, sizeY: Short, sky: Boolean, sea: Boolean, changeType: Int) {
        var mask = CollisionFlags.WALK
        if (sky) {
            mask = mask or CollisionFlags.FLY
        }
        if (sea) {
            mask = mask or CollisionFlags.SWIM
        }
        for (tileX in x until x + sizeX) {
            for (tileY in y until y + sizeY) {
                changeMask(tileX, tileY, plane, mask, changeType)
            }
        }
    }

    override fun changeWall(x: Int, y: Int, plane: Int, type: Int, rotation: Int, sky: Boolean, sea: Boolean, changeType: Int) {
        changeWall(x, y, plane, type, rotation, 0, changeType)
        if (sky) {
            changeWall(x, y, plane, type, rotation, 1, changeType)
        }
        if (sea) {
            changeWall(x, y, plane, type, rotation, 2, changeType)
        }
    }

    /**
     * Applies [changeType] changes to clipping mask for a wall
     * @param x The x coordinate of the wall
     * @param y The y coordinate of the wall
     * @param plane The plane coordinate of the wall
     * @param type The wall type
     * @param rotation The rotation of the wall
     * @param motion Which stage of the mask is being changes (0 normal, 1 projectile/flying, 2 alternative)
     * @param changeType How to change the clipping mask
     */
    private fun changeWall(x: Int, y: Int, plane: Int, type: Int, rotation: Int, motion: Int, changeType: Int) {
        if (type == 2) {
            val direction = ordinal[rotation and 0x3]
            changeMask(x, y, plane, either(direction), changeType)
            changeMask(x + direction.deltaX, y, plane, direction.horizontal().flag(), changeType)
            changeMask(x, y + direction.deltaY, plane, direction.vertical().flag(), changeType)
        } else {
            val direction = when (type) {
                0 -> cardinal[(rotation + 3) and 0x3]
                1, 3 -> ordinal[rotation and 0x3]
                else -> return
            }
            changeMask(x, y, plane, direction.flag(motion), changeType)
            changeMask(x + direction.deltaX, y + direction.deltaY, plane, direction.inverse().flag(motion), changeType)
        }
    }

    override fun changeMask(x: Int, y: Int, plane: Int, mask: Int, changeType: Int) {
        val regionId = Position.regionId(x, y)
        val entityId = regions?.getEntityId(regionId) ?: return
        val clipping = getClipping(entityId) ?: return
        val localX = x % 64
        val localY = y % 64
        when (changeType) {
            ADD_MASK -> clipping.addMask(localX, localY, plane, mask)
            REMOVE_MASK -> clipping.removeMask(localX, localY, plane, mask)
            SET_MASK -> clipping.setMask(localX, localY, plane, mask)
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
}