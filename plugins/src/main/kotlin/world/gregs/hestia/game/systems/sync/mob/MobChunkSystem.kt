package world.gregs.hestia.game.systems.sync.mob

import com.artemis.ComponentMapper
import world.gregs.hestia.game.component.entity.ClientIndex
import world.gregs.hestia.game.component.entity.Mob
import world.gregs.hestia.game.component.map.Position
import world.gregs.hestia.game.systems.extensions.SubscriptionSystem
import world.gregs.hestia.game.systems.sync.EntityChunkSystem
import world.gregs.hestia.services.Aspect
import world.gregs.hestia.game.map.MapConstants
import world.gregs.hestia.game.systems.sync.MobChunkMap


class MobChunkSystem : EntityChunkSystem(Mob::class) {

    private lateinit var map: MobChunkMap
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
        val hash = EntityChunkSystem.toHash(position)
        map.add(hash, entityId)
    }

    /**
     * Removes an entity from it's chunk
     * @param entityId [Int]
     */
    override fun removed(entityId: Int) {
        val position = positionMapper.get(entityId)
        val hash = EntityChunkSystem.toHash(position)
        map.remove(hash, entityId)
    }
}