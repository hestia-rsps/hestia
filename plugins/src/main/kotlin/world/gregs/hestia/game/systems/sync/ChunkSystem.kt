package world.gregs.hestia.game.systems.sync

import com.artemis.Component
import com.artemis.ComponentMapper
import world.gregs.hestia.game.component.entity.ClientIndex
import world.gregs.hestia.game.component.map.Position
import world.gregs.hestia.game.systems.extensions.SubscriptionSystem
import world.gregs.hestia.services.Aspect
import world.gregs.hestia.game.map.MapConstants
import kotlin.reflect.KClass

abstract class ChunkSystem(type: KClass<out Component>) : SubscriptionSystem(Aspect.all(ClientIndex::class, type)) {

    private lateinit var positionMapper: ComponentMapper<Position>
    private val map = HashMap<Int, ArrayList<Int>>()

    /**
     * Returns a list of all the entities local to a chunk
     * @param position initial chunk
     * @param size How large of a radius of chunks to get
     * @return list of all [type] entity ids
     */
    fun get(position: Position, size: Int = MapConstants.MAP_SIZES[0] shr 4): List<Int> {
        val xRange = position.chunkX - size .. position.chunkX + size
        val yRange = position.chunkY - size .. position.chunkY + size
        val plane = position.plane
        val list = ArrayList<Int>()
        for(x in xRange) {
            for(y in yRange) {
                val hash = ChunkSystem.toHash(x, y, plane)
                if(map.containsKey(hash)) {
                    list.addAll(map[hash]!!)
                }
            }
        }
        return list
    }

    /**
     * Inserts an entity into it's chunk
     * @param entityId [Int]
     */
    override fun inserted(entityId: Int) {
        val position = positionMapper.get(entityId)

        val hash = ChunkSystem.toHash(position)
        if(!map.containsKey(hash)) {
            map[hash] = ArrayList()
        }

        map[hash]?.add(entityId)
    }

    /**
     * Removes an entity from it's chunk
     * @param entityId [Int]
     */
    override fun removed(entityId: Int) {
        val position = positionMapper.get(entityId)
        val hash = ChunkSystem.toHash(position)
        if(map.containsKey(hash)) {
            map[hash]?.remove(entityId)
        }
    }

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