package worlds.gregs.hestia.core.display.client.logic.systems.region

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import net.mostlyoriginal.api.event.common.EventSystem
import net.mostlyoriginal.api.event.common.Subscribe
import worlds.gregs.hestia.artemis.*
import worlds.gregs.hestia.core.display.client.model.components.Viewport
import worlds.gregs.hestia.core.display.client.model.events.UpdateMapRegion
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.world.map.model.Chunk.toChunkPosition
import worlds.gregs.hestia.core.world.map.model.MapConstants.MAP_SIZES
import worlds.gregs.hestia.core.world.map.model.MapConstants.PLANE_RANGE
import worlds.gregs.hestia.core.world.region.api.Dynamic
import worlds.gregs.hestia.core.world.region.api.Regions
import worlds.gregs.hestia.core.world.region.logic.systems.RegionBuilderSystem.Companion.forChunks
import worlds.gregs.hestia.network.client.encoders.messages.MapRegion
import worlds.gregs.hestia.network.client.encoders.messages.MapRegionDynamic

@Wire(failOnNull = false)
class RegionSenderSystem : SubscriptionSystem(Aspect.all(Position::class, Viewport::class)) {

    private lateinit var es: EventSystem
    private var regions: Regions? = null
    private var dynamic: Dynamic? = null
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var viewportMapper: ComponentMapper<Viewport>

    override fun inserted(entityId: Int) {
        //Not recommended for dynamic region on login as if too many chunks it'll go over the packet size limit
        send(entityId, local = true, forceRefresh = false)
    }

    @Subscribe
    fun send(event: UpdateMapRegion) {
        send(event.entityId, event.local, event.forceRefresh)
    }

    fun send(entityId: Int, local: Boolean, forceRefresh: Boolean) {
        val position = positionMapper.get(entityId)
        val regionEntityId = regions?.getEntityId(position.regionId)
        val positions = if(local) updateAllPositions(entityId) else null
        val location = if(local) position.locationHash30Bit else null
        if(regionEntityId != null && dynamic?.isDynamic(regionEntityId) == true) {
            val mapHash = MAP_SIZES[0] shr 4
            val data = encodeDynamicData(position, mapHash)
            es.send(entityId, MapRegionDynamic(position.chunkX, position.chunkY, forceRefresh, 0, mapHash, positions, location, data.first, data.second))
        } else {
            es.send(entityId, MapRegion(position.chunkX, position.chunkY, forceRefresh, 0, MAP_SIZES[0] shr 4, positions, location))
        }
    }

    private fun updateAllPositions(entityId: Int): IntArray {
        val list = mutableListOf<Int>()
        val viewport = viewportMapper.get(entityId)
        entityIds.toArray().filterNot { it == entityId }.forEach { player ->
            val pos = positionMapper.get(player)
            val hash = pos.locationHash18Bit
            viewport.updatePosition(player, pos)
            list.add(hash)
        }
        return list.toTypedArray().toIntArray()
    }

    private fun encodeDynamicData(position: Position, mapHash: Int): Pair<List<Int?>, Int> {
        var chunkCount = 0
        val list = ArrayList<Int?>()
        //For all chunks within view
        forChunks(position.chunkX.nearby(mapHash), position.chunkY.nearby(mapHash), PLANE_RANGE) { chunkX, chunkY, plane ->
            //Calculate region id
            val regionId = (chunkX / 8 shl 8) + chunkY / 8
            //Get id of it's region
            val regionEntityId = regions!!.getEntityId(regionId)

            //Check if region exists and is a dynamic region
            if (regionEntityId == null || !dynamic!!.isDynamic(regionEntityId)) {
                list.add(null)//Send blank chunk
                return@forChunks
            }

            //Calculate the chunks shift
            val hash = toChunkPosition(chunkX, chunkY, plane)
            //Get the dynamic region data
            val data = dynamic!!.get(regionEntityId)!!.regionData[hash]
            list.add(data)
            if(data != null) {
                chunkCount++
            }
        }
        return Pair(list, chunkCount)
    }
}