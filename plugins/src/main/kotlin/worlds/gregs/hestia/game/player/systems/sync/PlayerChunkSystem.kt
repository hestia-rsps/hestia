package worlds.gregs.hestia.game.player.systems.sync

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.player.component.Player
import worlds.gregs.hestia.game.component.map.Position
import worlds.gregs.hestia.game.map.MapConstants
import worlds.gregs.hestia.game.entity.systems.EntityChunkSystem

class PlayerChunkSystem : EntityChunkSystem(Player::class) {

    private lateinit var map: PlayerChunkMap
    private lateinit var positionMapper: ComponentMapper<Position>

    /**
     * Returns a list of all the entities local to a chunk
     * @param position initial chunk
     * @param size How large of a radius of chunks to get
     * @return list of all [type] entity ids
     */
    override fun get(position: Position): List<Int> {
        val size = MapConstants.MAP_SIZES[0] shr 4
        val xRange = position.chunkX - size .. position.chunkX + size
        val yRange = position.chunkY - size .. position.chunkY + size
        return map.getAll(xRange, yRange, position.plane)
    }

    /**
     * Inserts an entity into it's chunk
     * @param entityId [Int]
     */
    override fun inserted(entityId: Int) {
        val position = positionMapper.get(entityId)
        val hash = toHash(position)
        map.add(hash, entityId)
    }

    /**
     * Removes an entity from it's chunk
     * @param entityId [Int]
     */
    override fun removed(entityId: Int) {
        val position = positionMapper.get(entityId)
        val hash = toHash(position)
        map.remove(hash, entityId)
    }
}