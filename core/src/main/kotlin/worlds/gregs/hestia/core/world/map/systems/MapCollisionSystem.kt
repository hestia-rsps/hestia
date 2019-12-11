package worlds.gregs.hestia.core.world.map.systems

import com.artemis.annotations.Wire
import worlds.gregs.hestia.api.collision.Collision
import worlds.gregs.hestia.game.entity.components.Position
import worlds.gregs.hestia.api.map.ClippingMasks
import worlds.gregs.hestia.api.map.Map
import worlds.gregs.hestia.api.map.TileClipping
import worlds.gregs.hestia.api.region.Regions
import worlds.gregs.hestia.game.map.Chunk.getRotatedChunkRotation
import worlds.gregs.hestia.game.map.Chunk.toChunkPosition
import worlds.gregs.hestia.game.entity.movement.PrimaryNavigation
import worlds.gregs.hestia.game.entity.movement.SecondaryNavigation
import worlds.gregs.hestia.game.entity.movement.TertiaryNavigation
import worlds.gregs.hestia.core.world.region.logic.systems.DynamicSystem
import worlds.gregs.hestia.game.client.update.block.Direction

/**
 * TileClipping
 * Checks whether an entity can move in a direction without being blocked by an obstacle
 */
@Wire(failOnNull = false)
class MapCollisionSystem : TileClipping() {
    private var masks: ClippingMasks? = null
    private var map: Map? = null
    private var regions: Regions? = null
    private var dynamic: DynamicSystem? = null
    private var collision: Collision? = null
    private lateinit var primary: PrimaryNavigation
    private lateinit var secondary: SecondaryNavigation
    private lateinit var tertiary: TertiaryNavigation

    override fun initialize() {
        super.initialize()
        primary = PrimaryNavigation(collision)
        secondary = SecondaryNavigation(collision)
        tertiary = TertiaryNavigation(collision)
    }

    //TODO remove these
    override fun getMask(x: Int, y: Int, plane: Int): Int {
        val regionId = Position.regionId(x, y)
        val entityId = regions?.getEntityId(regionId) ?: return -1
        val clipping = map?.getClipping(entityId) ?: return -1
        return masks?.getMask(entityId, clipping, x % 64, y % 64, plane) ?: -1
    }

    override fun getRotation(x: Int, y: Int, plane: Int): Int {
        val regionId = Position.regionId(x, y)
        val entityId = regions?.getEntityId(regionId) ?: return 0
        val dynamic = dynamic?.get(entityId) ?: return 0
        val shift = dynamic.regionData[toChunkPosition(x shr 3, y shr 3, plane)] ?: return 0
        return getRotatedChunkRotation(shift)
    }

    override fun traversable(dir: Direction, x: Int, y: Int, plane: Int, width: Int, height: Int): Boolean {
        var deltaX = dir.deltaX
        var deltaY = dir.deltaY
        var direction = dir
        //Find the rotation of the chunk
        val rotation = getRotation(x + deltaX, y + deltaY, plane)

        if (rotation != 0) {//TODO not needed as clipping will already have been rotated?
            //Apply rotation to deltas
            for (rotate in 0 until 4 - rotation) {
                val temp = deltaX
                deltaX = deltaY
                deltaY = 0 - temp
            }

            //New rotated direction
            direction = Direction.fromDelta(deltaX, deltaY) ?: return false
        }

        //Choose optimal navigator to use
        return when {
            width == 1 && height == 1 -> primary
            width == 2 && height == 2 -> secondary
            else -> tertiary
        }.traversable(direction, x % 64, y % 64, width, height, deltaX, deltaY)
    }
}