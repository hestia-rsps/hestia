package worlds.gregs.hestia.game.plugins.entity.systems.map

import com.artemis.Component
import worlds.gregs.hestia.game.plugins.core.components.entity.ClientIndex
import worlds.gregs.hestia.game.plugins.core.components.map.Position
import worlds.gregs.hestia.game.plugins.core.systems.extensions.SubscriptionSystem
import worlds.gregs.hestia.services.Aspect
import kotlin.reflect.KClass

abstract class EntityChunkSystem(type: KClass<out Component>) : SubscriptionSystem(Aspect.all(ClientIndex::class, type)) {

    /**
     * Returns a list of all the entities local to a chunk
     * @param position initial chunk
     * @param size How large of a radius of chunks to get
     * @return list of all [type] entity ids
     */
    abstract fun get(position: Position/*, size: Int = MapConstants.MAP_SIZES[0] shr 4*/): List<Int>


    companion object {

        fun toHash(position: Position): Int {
            return toHash(position.chunkX, position.chunkY, position.plane)
        }

        fun toHash(chunkX: Int, chunkY: Int, plane: Int): Int {
            return chunkY + (chunkX shl 14) + (plane shl 28)
        }

        fun fromHash(hash: Int): Position {
            val x = hash shr 14 and 0x3fff
            val y = hash and 0x3fff
            val z = hash shr 28
            return Position.create(x, y, z)
        }
    }
}