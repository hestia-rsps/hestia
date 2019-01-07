package worlds.gregs.hestia.network.game.out

import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.services.int
import worlds.gregs.hestia.api.core.components.Position
import worlds.gregs.hestia.api.region.Dynamic
import worlds.gregs.hestia.api.region.Regions
import worlds.gregs.hestia.game.map.Chunk.toChunkPosition
import worlds.gregs.hestia.game.map.MapConstants.MAP_SIZES
import worlds.gregs.hestia.game.map.MapConstants.PLANE_RANGE
import worlds.gregs.hestia.services.nearby

class DynamicMapRegion(entityId: Int, position: Position, forceReload: Boolean, login: (Packet.Builder.(Int) -> Unit)?, regions: Regions, dynamic: Dynamic) : Packet.Builder(128, Packet.Type.VAR_SHORT) {
    init {
        login?.invoke(this, entityId)

        writeShort(position.chunkY)//ChunkY
        writeByte(0)//Map size
        writeByteS(forceReload.int)//force next map load refresh
        writeByteS(3)//Was at dynamic region? 5 or 3 TODO test
        writeLEShort(position.chunkX)//ChunkX

        val mapHash = MAP_SIZES[0] shr 4
        var chunkCount = 0

        startBitAccess()

        //For all chunks within view
        forChunks(position.chunkX.nearby(mapHash), position.chunkY.nearby(mapHash), PLANE_RANGE) { chunkX, chunkY, plane ->
            //Calculate region id
            val regionId = (chunkX / 8 shl 8) + chunkY / 8
            //Get id of it's region
            val regionEntityId = regions.getEntityId(regionId)

            //Check if region exists and is a dynamic region
            if (regionEntityId == null || !dynamic.isDynamic(regionEntityId)) {
                writeBits(1, 0)//Send blank chunk
                return@forChunks
            }

            //Calculate the chunks shift
            val hash = toChunkPosition(chunkX, chunkY, plane)
            //Get the dynamic region data
            val data = dynamic.get(regionEntityId)!!.regionData[hash]
            //Write if the data is valid
            writeBits(1, (data != null).int)
            //If valid data
            if (data != null) {
                //Write
                writeBits(26, data)
                chunkCount++
            }
        }

        finishBitAccess()

        //XTEA keys (values no longer needed but count is used to calculate chunk count)
        for (index in 0 until chunkCount) {
            for (keyIndex in 0 until 4) {
                writeInt(0)
            }
        }
    }

    //FIXME RegionBuilderSystem.kt
    inline fun forChunks(rangeX: IntRange, rangeY: IntRange, rangeZ: IntRange, action: (Int, Int, Int) -> Unit) {
        for (plane in rangeZ) {
            for (chunkX in rangeX) {
                for (chunkY in rangeY) {
                    action.invoke(chunkX, chunkY, plane)
                }
            }
        }
    }
}