package worlds.gregs.hestia.api.map

import com.artemis.Aspect
import com.artemis.utils.IntBag
import net.mostlyoriginal.api.utils.QuadTree
import worlds.gregs.hestia.api.movement.components.Shift
import worlds.gregs.hestia.artemis.SubscriptionSystem
import worlds.gregs.hestia.game.entity.components.Position
import worlds.gregs.hestia.game.map.MapConstants

/**
 * An array of [QuadTree]s representing the entire game map, for storing entities
 */
abstract class QuadMap(builder: Aspect.Builder) : SubscriptionSystem(builder) {

    /**
     * Updates an entities position in the entity map
     * @param entityId The id of the entity to update
     * @param shift How much the entity has shifted since last update
     */
    abstract fun update(entityId: Int, shift: Shift)

    /**
     * Sets the entity to a tile
     * @param entityId The id of the entity to set
     * @param hash The tile hash the entity is on
     */
    internal abstract fun setTile(entityId: Int, hash: Int)

    /**
     * Removes an entity from a tile
     * @param entityId The id of the entity to remove
     * @param hash The tile hash the entity was on
     */
    internal abstract fun removeFromTile(entityId: Int, hash: Int)

    /**
     * Faster look up of all entities on a tile
     * @param hash The tile hash to get entities for
     * @return List of all entities on the tile
     */
    abstract fun getTile(hash: Int): ArrayList<Int>?

    /**
     * Inserts an entity into the entity map
     * @param entityId The id of the entity to add
     * @param x The entities x coordinate
     * @param y The entities y coordinate
     * @param plane The entities plane coordinate
     * @param width The entities width (default 1)
     * @param height The entities height (default 1)
     */
    internal abstract fun insert(entityId: Int, x: Int, y: Int, plane: Int, width: Int = 1, height: Int = 1)

    /**
     * Updates an entity in the entity map
     * @param entityId The id of the entity to update
     * @param x The entities new x coordinate
     * @param y The entities new y coordinate
     * @param plane The entities new plane coordinate
     * @param width The entities new width (default 1)
     * @param height The entities new height (default 1)
     */
    protected abstract fun update(entityId: Int, x: Int, y: Int, plane: Int, width: Int = 1, height: Int = 1)

    /**
     * Removes an entity from the map
     * @param entityId The id of the entity to remove
     * @param plane The plane the entity is currently on
     */
    protected abstract fun remove(entityId: Int, plane: Int)

    /**
     * Fills bag with all entities on a single tile
     * @param fill The bag to fill with entity ids
     * @param x The tile x coordinate
     * @param y The tile y coordinate
     * @param plane The tile plane coordinate (default 0)
     * @return The filled bag
     */
    abstract fun getExact(fill: IntBag, x: Int, y: Int, plane: Int = 0): IntBag

    /**
     * Fills bag with all entities within a radius of a point
     * Warning: Exact square, results will need filtering for actual radius
     * @param fill The bag to fill with entity ids
     * @param x The points x coordinate
     * @param y The points y coordinate
     * @param plane The points plane coordinate
     * @param width The points width
     * @param height The points height
     * @param radius The radius around the point to collect entities
     * @return The filled bag
     */
    fun getExact(fill: IntBag, x: Int, y: Int, plane: Int, width: Int = 1, height: Int = 1, radius: Int): IntBag {
        return getExact(fill, x - radius, y - radius, plane, width + radius * 2, height + radius * 2)
    }

    /**
     * Fills bag with all entities in a area
     * @param fill The bag to fill with entity ids
     * @param x The areas x coordinate
     * @param y The areas y coordinate
     * @param plane The areas plane coordinate
     * @param width The areas width (includes width of x)
     * @param height The areas height (includes height of y)
     * @return The filled bag
     */
    abstract fun getExact(fill: IntBag, x: Int, y: Int, plane: Int, width: Int, height: Int): IntBag

    /**
     * Fills bag with all entities within a radius of a point
     * Warning: Not exact, results will need filtering
     * @param fill The bag to fill with entity ids
     * @param position The points position
     * @param width The points width
     * @param height The points height
     * @param radius The radius around the point to collect entities
     * @return The filled bag
     */
    open fun get(fill: IntBag, position: Position, width: Int = 1, height: Int = 1, radius: Int): IntBag {
        return get(fill, position.x, position.y, position.plane, width, height, radius)
    }

    /**
     * Fills bag with all entities within a radius of a point
     * Warning: Not exact, results will need filtering
     * @param fill The bag to fill with entity ids
     * @param x The points x coordinate
     * @param y The points y coordinate
     * @param plane The points plane coordinate
     * @param width The points width
     * @param height The points height
     * @param radius The radius around the point to collect entities
     * @return The filled bag
     */
    fun get(fill: IntBag, x: Int, y: Int, plane: Int, width: Int = 1, height: Int = 1, radius: Int): IntBag {
        return get(fill, x - radius, y - radius, plane, width + radius * 2, height + radius * 2)
    }

    /**
     * Fills bag with all entities within an area
     * Warning: Not exact, results will need filtering
     * @param fill The bag to fill with entity ids
     * @param x The points x coordinate
     * @param y The points y coordinate
     * @param plane The points plane coordinate
     * @param width The width of the area to query
     * @param height The height of the area to query
     * @return The filled bag
     */
    abstract fun get(fill: IntBag, x: Int, y: Int, plane: Int, width: Int, height: Int): IntBag

    companion object {
        internal const val MAP_SIZE = (MapConstants.REGION_COUNT * MapConstants.REGION_SIZE).toFloat()//Number of tiles in the map
    }
}