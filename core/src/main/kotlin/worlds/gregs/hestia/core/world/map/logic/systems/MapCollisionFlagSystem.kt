package worlds.gregs.hestia.core.world.map.logic.systems

import com.artemis.annotations.Wire
import worlds.gregs.hestia.core.display.update.model.Direction.Companion.cardinal
import worlds.gregs.hestia.core.display.update.model.Direction.Companion.ordinal
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.world.collision.model.CollisionFlag
import worlds.gregs.hestia.core.world.collision.model.CollisionFlag.either
import worlds.gregs.hestia.core.world.collision.model.CollisionFlag.flag
import worlds.gregs.hestia.core.world.map.api.Collisions
import worlds.gregs.hestia.core.world.map.api.MapCollisionFlags
import worlds.gregs.hestia.core.world.map.api.Map
import worlds.gregs.hestia.core.world.region.api.Regions

@Wire(failOnNull = false)
class MapCollisionFlagSystem : MapCollisionFlags() {

    private var map: Map? = null
    private var regions: Regions? = null

    override fun changeObject(x: Int, y: Int, plane: Int, sizeX: Short, sizeY: Short, sky: Boolean, sea: Boolean, changeType: Int) {
        var flag = CollisionFlag.WALK
        if (sky) {
            flag = flag or CollisionFlag.FLY
        }
        if (sea) {
            flag = flag or CollisionFlag.SWIM
        }
        for (tileX in x until x + sizeX) {
            for (tileY in y until y + sizeY) {
                changeFlag(tileX, tileY, plane, flag, changeType)
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
     * Applies [changeType] changes to collision flag for a wall
     * @param x The x coordinate of the wall
     * @param y The y coordinate of the wall
     * @param plane The plane coordinate of the wall
     * @param type The wall type
     * @param rotation The rotation of the wall
     * @param motion Which stage of the flag is being changes (0 walk, 1 fly, 2 swim)
     * @param changeType How to change the collision flag
     */
    private fun changeWall(x: Int, y: Int, plane: Int, type: Int, rotation: Int, motion: Int, changeType: Int) {
        if (type == 2) {
            val direction = ordinal[rotation and 0x3]
            changeFlag(x, y, plane, either(direction), changeType)
            changeFlag(x + direction.deltaX, y, plane, direction.horizontal().flag(), changeType)
            changeFlag(x, y + direction.deltaY, plane, direction.vertical().flag(), changeType)
        } else {
            val direction = when (type) {
                0 -> cardinal[(rotation + 3) and 0x3]
                1, 3 -> ordinal[rotation and 0x3]
                else -> return
            }
            changeFlag(x, y, plane, direction.flag(motion), changeType)
            changeFlag(x + direction.deltaX, y + direction.deltaY, plane, direction.inverse().flag(motion), changeType)
        }
    }

    override fun changeFlag(x: Int, y: Int, plane: Int, flag: Int, changeType: Int) {
        val regionId = Position.regionId(x, y)
        val entityId = regions?.getEntityId(regionId) ?: return
        val collisions = getCollision(entityId) ?: return
        val localX = x % 64
        val localY = y % 64
        when (changeType) {
            ADD_FLAG -> collisions.addFlag(localX, localY, plane, flag)
            REMOVE_FLAG -> collisions.removeFlag(localX, localY, plane, flag)
            SET_FLAG -> collisions.setFlag(localX, localY, plane, flag)
        }
    }

    /**
     * Returns collisions of region
     * @param entityId Regions entity id
     * @return collision map
     */
    private fun getCollision(entityId: Int): Collisions? {
        return map?.createCollision(entityId)
    }
}