package worlds.gregs.hestia.core.entity.item.floor.logic.systems

import com.artemis.ComponentMapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.artemis.ParallelSystem
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.item.floor.api.FloorItems
import worlds.gregs.hestia.core.entity.item.floor.model.components.ReloadFloorItems
import worlds.gregs.hestia.core.world.map.logic.Spiral
import worlds.gregs.hestia.core.world.map.model.Chunk
import worlds.gregs.hestia.core.world.map.model.MapConstants

/**
 * Updates floor items for chunks which have changed.
 */
class FloorItemSyncSystem : ParallelSystem(Aspect.all(Position::class, ReloadFloorItems::class)) {

    private lateinit var reloadFloorItemsMapper: ComponentMapper<ReloadFloorItems>
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var floorItems: FloorItems

    override fun processAsync(entityId: Int) = GlobalScope.async {
        val position = positionMapper.get(entityId)
        val size = (MapConstants.MAP_SIZES[0] shr 4) / 2
        //For all chunks in view
        Spiral.spiral(size).forEach { point ->
            val chunkX = position.chunkX + point.first
            val chunkY = position.chunkY + point.second
            val chunk = Chunk.toChunkPosition(chunkX, chunkY, position.plane)
            //Send/resend items
            floorItems.sendFloorItems(entityId, chunk, true)
        }
        reloadFloorItemsMapper.remove(entityId)
    }
}