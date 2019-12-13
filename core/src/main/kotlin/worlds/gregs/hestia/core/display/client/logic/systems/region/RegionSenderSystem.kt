package worlds.gregs.hestia.core.display.client.logic.systems.region

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import net.mostlyoriginal.api.event.common.EventSystem
import net.mostlyoriginal.api.event.common.Subscribe
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.GameConstants
import worlds.gregs.hestia.artemis.SubscriptionSystem
import worlds.gregs.hestia.core.display.client.model.components.Viewport
import worlds.gregs.hestia.core.display.client.model.events.UpdateMapRegion
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.world.map.model.Chunk.toChunkPosition
import worlds.gregs.hestia.core.world.map.model.MapConstants.MAP_SIZES
import worlds.gregs.hestia.core.world.map.model.MapConstants.PLANE_RANGE
import worlds.gregs.hestia.core.world.region.api.Dynamic
import worlds.gregs.hestia.core.world.region.api.Regions
import worlds.gregs.hestia.network.client.encoders.messages.MapRegion
import worlds.gregs.hestia.network.client.encoders.messages.MapRegionDynamic
import worlds.gregs.hestia.service.Aspect
import worlds.gregs.hestia.service.nearby
import worlds.gregs.hestia.service.send
import worlds.gregs.hestia.service.toArray

@Wire(failOnNull = false)
class RegionSenderSystem : SubscriptionSystem(Aspect.all(Position::class, Viewport::class)) {

    private lateinit var es: EventSystem
    private var regions: Regions? = null
    private var dynamic: Dynamic? = null
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var viewportMapper: ComponentMapper<Viewport>

    override fun inserted(entityId: Int) {
        //Not recommended for dynamic region on login as if too many chunks it'll go over the packet size limit
        send(entityId, true, false)
    }

    private val login: PacketBuilder.(Int) -> Unit = { entityId ->
        val position = positionMapper.get(entityId)
        val viewport = viewportMapper.get(entityId)

        startBitAccess()
        //Send current player position
        writeBits(30, position.locationHash30Bit)

        //Update player locations
        entityIds.toArray().filterNot { it == entityId }.forEach { player ->
            val pos = positionMapper.get(player)
            val hash = pos.locationHash18Bit
            viewport.updatePosition(player, pos)
            writeBits(18, hash)
        }

        //Iterate up to max number of players
        for(i in (entityIds.size() + 1) until GameConstants.PLAYERS_LIMIT) {
            writeBits(18, 0)
        }

        finishBitAccess()
    }

    @Subscribe
    fun send(event: UpdateMapRegion) {
        send(event.entityId, event.local, event.forceRefresh)
    }

    fun send(entityId: Int, local: Boolean, forceRefresh: Boolean) {
        val position = positionMapper.get(entityId)
        val regionEntityId = regions?.getEntityId(position.regionId)
        if(regionEntityId != null && dynamic?.isDynamic(regionEntityId) == true) {
            val mapHash = MAP_SIZES[0] shr 4
            val data = encodeDynamicData(position, mapHash)
            es.send(entityId, MapRegionDynamic(entityId, position.chunkX, position.chunkY, forceRefresh, 0, mapHash, if (local) login else null, data.first, data.second))
        } else {
            es.send(entityId, MapRegion(entityId, position.chunkX, position.chunkY, forceRefresh, 0, MAP_SIZES[0] shr 4, if (local) login else null))
        }
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

    //FIXME RegionBuilderSystem.kt
    private inline fun forChunks(rangeX: IntRange, rangeY: IntRange, rangeZ: IntRange, action: (Int, Int, Int) -> Unit) {
        for (plane in rangeZ) {
            for (chunkX in rangeX) {
                for (chunkY in rangeY) {
                    action.invoke(chunkX, chunkY, plane)
                }
            }
        }
    }
}