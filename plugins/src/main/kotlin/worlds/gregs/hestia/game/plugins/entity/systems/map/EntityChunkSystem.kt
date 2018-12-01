package worlds.gregs.hestia.game.plugins.entity.systems.map

import com.artemis.Component
import worlds.gregs.hestia.game.plugins.core.components.entity.ClientIndex
import worlds.gregs.hestia.game.plugins.core.components.map.Position
import worlds.gregs.hestia.game.plugins.core.systems.extensions.SubscriptionSystem
import worlds.gregs.hestia.game.region.MapConstants
import worlds.gregs.hestia.services.Aspect
import kotlin.reflect.KClass

abstract class EntityChunkSystem(type: KClass<out Component>) : SubscriptionSystem(Aspect.all(ClientIndex::class, type)) {

    abstract fun getMap(): EntityChunkMap

    /**
     * Returns a list of all the entities local to a chunk
     * @param position initial chunk
     * @param size How large of a radius of chunks to get TODO
     * @return list of all entity ids
     */
    fun get(position: Position): List<Int> {
        val size = MapConstants.MAP_SIZES[0] shr 4
        val xRange = position.chunkX - size .. position.chunkX + size
        val yRange = position.chunkY - size .. position.chunkY + size
        return getMap().getAll(xRange, yRange, position.plane)
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


    companion object {

        fun toChunkPosition(position: Position): Int {
            return toChunkPosition(position.chunkX, position.chunkY, position.plane)
        }

        fun toChunkPosition(chunkX: Int, chunkY: Int, plane: Int): Int {
            return chunkY + (chunkX shl 14) + (plane shl 28)
        }

        fun fromChunkPosition(hash: Int): Position {
            val x = hash shr 14 and 0x3fff
            val y = hash and 0x3fff
            val z = hash shr 28
            return Position.create(x, y, z)
        }
    }
}