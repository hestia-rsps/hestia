package worlds.gregs.hestia.core.world.map.logic.systems

import com.artemis.annotations.Wire
import worlds.gregs.hestia.core.display.update.model.Direction
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.world.collision.api.Collision
import worlds.gregs.hestia.core.world.map.api.TileCollision
import worlds.gregs.hestia.core.world.map.model.Chunk.getRotatedChunkRotation
import worlds.gregs.hestia.core.world.map.model.Chunk.toChunkPosition
import worlds.gregs.hestia.core.world.movement.logic.navigation.PrimaryNavigation
import worlds.gregs.hestia.core.world.movement.logic.navigation.SecondaryNavigation
import worlds.gregs.hestia.core.world.movement.logic.navigation.TertiaryNavigation
import worlds.gregs.hestia.core.world.region.api.Regions
import worlds.gregs.hestia.core.world.region.logic.systems.DynamicSystem

/**
 * Checks whether an entity can move in a direction without being blocked by an obstacle
 */
@Wire(failOnNull = false)
class MapCollisionSystem : TileCollision() {
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

    override fun getRotation(x: Int, y: Int, plane: Int): Int {
        val regionId = Position.regionId(x, y)
        val entityId = regions?.getEntityId(regionId) ?: return 0
        val dynamic = dynamic?.get(entityId) ?: return 0
        val shift = dynamic.regionData[toChunkPosition(x shr 3, y shr 3, plane)] ?: return 0
        return getRotatedChunkRotation(shift)
    }

    override fun traversable(dir: Direction, x: Int, y: Int, plane: Int, width: Int, height: Int): Boolean {
        //Choose optimal navigator to use
        return when {
            width == 1 && height == 1 -> primary
            width == 2 && height == 2 -> secondary
            else -> tertiary
        }.traversable(dir.inverse(), x, y, width, height, dir.deltaX, dir.deltaY)
    }
}