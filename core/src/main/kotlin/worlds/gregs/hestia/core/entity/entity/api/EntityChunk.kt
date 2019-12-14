package worlds.gregs.hestia.core.entity.entity.api

import com.artemis.Component
import com.artemis.ComponentMapper
import worlds.gregs.hestia.artemis.SubscriptionSystem
import worlds.gregs.hestia.core.display.client.model.components.ClientIndex
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.world.map.model.Chunk
import worlds.gregs.hestia.core.world.map.model.MapConstants
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.artemis.nearby
import kotlin.reflect.KClass

abstract class EntityChunk(type: KClass<out Component>) : SubscriptionSystem(Aspect.all(ClientIndex::class, type)) {

    abstract fun getMap(): EntityChunkMap

    private lateinit var positionMapper: ComponentMapper<Position>

    /**
     * Returns a list of all the entities local to a chunk
     * @param position initial chunk
     * @param size How large of a radius of chunks to get TODO
     * @return list of all entity ids
     */
    fun get(position: Position, size: Int = MapConstants.MAP_SIZES[0] shr 4): List<Int> {
        val xRange = position.chunkX.nearby(size)
        val yRange = position.chunkY.nearby(size)
        return getMap().getAll(xRange, yRange, position.plane)
    }

    /**
     * Checks if tile has an entity
     * @param x The x coordinate to check
     * @param y The y coordinate to check
     * @param plane The plane coordinate to check
     * @return Whether an entity exists on the tile
     */
    fun has(x: Int, y: Int, plane: Int): Boolean {
        return getMap().get(Chunk.toChunkPosition(x / 8, y / 8, plane))?.any { positionMapper.has(it) && positionMapper.get(it).same(x, y, plane) } ?: false
    }

    /**
     * Returns a list of all the entities in a region
     * @param regionId
     * @return list of all entity ids
     */
    fun get(regionId: Int, plane: Int): List<Int> {
        val regionX = (regionId shr 8) * 8
        val regionY = (regionId and 0xff) * 8
        val xRange = regionX .. regionX + 8
        val yRange = regionY .. regionY + 8
        return getMap().getAll(xRange, yRange, plane)
    }
}