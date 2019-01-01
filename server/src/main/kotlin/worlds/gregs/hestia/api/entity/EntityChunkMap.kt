package worlds.gregs.hestia.api.entity

import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.game.map.Chunk.toChunkPosition

abstract class EntityChunkMap : PassiveSystem() {
    private val map = HashMap<Int, ArrayList<Int>>()

    /**
     * Returns a list of all entities in the range of chunks specified
     * @param xRange ChunkX range
     * @param yRange ChunkY range
     * @param plane
     * @return list of entity ids
     */
    fun getAll(xRange: IntRange, yRange: IntRange, plane: Int): List<Int> {
        val list = ArrayList<Int>()
        for(chunkX in xRange) {
            for(chunkY in yRange) {
                val hash = toChunkPosition(chunkX, chunkY, plane)
                if(map.containsKey(hash)) {
                    list.addAll(map[hash]!!)
                }
            }
        }
        return list
    }

    /**
     * Returns the list of entities at a chunk
     * @param hash The chunk hash
     * @return List of entity ids (if exists) if not null
     */
    fun get(hash: Int): List<Int>? {
        return map[hash]
    }

    /**
     * Adds an entity id to a chunk
     * Creates the chunk if it hasn't be created yet.
     */
    fun add(hash: Int, entityId: Int) {
        if(hash < 0) {
            return
        }

        if(!map.containsKey(hash)) {
            map[hash] = ArrayList()
        }

        map[hash]?.add(entityId)
    }

    /**
     * Removes entity id from a chunk
     */
    fun remove(hash: Int, entityId: Int) {
        if(hash < 0) {
            return
        }

        if(map.containsKey(hash)) {
            map[hash]?.remove(entityId)
        }
    }
}