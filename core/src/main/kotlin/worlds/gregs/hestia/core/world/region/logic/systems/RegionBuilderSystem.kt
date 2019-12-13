package worlds.gregs.hestia.core.world.region.logic.systems

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import worlds.gregs.hestia.core.world.map.model.Chunk.toChunkPosition
import worlds.gregs.hestia.core.world.map.model.Chunk.toRotatedChunkPosition
import worlds.gregs.hestia.core.world.map.model.MapConstants.PLANE_RANGE
import worlds.gregs.hestia.core.world.region.api.RegionBuilder
import worlds.gregs.hestia.core.world.region.api.Regions
import worlds.gregs.hestia.core.world.region.model.components.DynamicRegion
import worlds.gregs.hestia.core.world.region.model.components.Loaded
import worlds.gregs.hestia.core.world.region.model.components.Loading
import worlds.gregs.hestia.core.world.region.model.components.RegionIdentifier

/**
 * DynamicRegionBuilder
 * Toolset to modify region maps
 * Uses exact chunk coordinates, not local
 */
@Wire(failOnNull = false)
class RegionBuilderSystem : RegionBuilder() {

    /*
        Key
        Tile - 1x1 square
        Chunk - 8x8x1 tiles
        Plane - 8x8x1 chunks
        Region - 1x1x4 planes

    */

    private var regions: Regions? = null
    private lateinit var dynamicMapper: ComponentMapper<DynamicRegion>
    private lateinit var regionMapper: ComponentMapper<RegionIdentifier>
    private lateinit var loadedMapper: ComponentMapper<Loaded>
    private lateinit var loadingMapper: ComponentMapper<Loading>

    internal val set = HashMap<Int, Int>()
    internal val clear = ArrayList<Int>()

    /**
     * Sets a single chunk at [chunkX], [chunkY], [plane] to the chunk at [toChunkX], [toChunkY], [toPlane] with [rotation]
     *
     * @param chunkX The x position of the chunk to replace
     * @param chunkY The y position of the chunk to replace
     * @param plane The z position of the chunk to replace
     * @param toChunkX The x position of the replacement chunk
     * @param toChunkY The y position of the replacement chunk
     * @param toPlane The z position of the replacement chunk
     * @param rotation The rotation of the new chunk
     */
    override fun set(chunkX: Int, chunkY: Int, plane: Int, toChunkX: Int, toChunkY: Int, toPlane: Int, rotation: Int) {
        set[toChunkPosition(chunkX, chunkY, plane)] =
                toRotatedChunkPosition(toChunkX, toChunkY, toPlane, rotation)
    }

    /**
     * Sets an area [width]x[height] of chunks with [rotation]
     *
     * @param chunkX The x position of the chunk to replace
     * @param chunkY The y position of the chunk to replace
     * @param plane The z position of the chunk to replace
     * @param toChunkX The x position of the replacement chunk
     * @param toChunkY The y position of the replacement chunk
     * @param toPlane The z position of the replacement chunk
     * @param width The number of chunks to replace on the x axis
     * @param height The number of chunks to replace on the y axis
     * @param rotation The rotation of the new chunks (Rotation is applied to the whole area)
     */
    override fun set(chunkX: Int, chunkY: Int, plane: Int, toChunkX: Int, toChunkY: Int, toPlane: Int, width: Int, height: Int, rotation: Int) {
        area(width, height, rotation) { fromX, fromY, toX, toY ->
            set(chunkX + fromX, chunkY + fromY, plane, toChunkX + toX, toChunkY + toY, toPlane, rotation)
        }
    }

    /**
     * Set's the whole region [regionId] to [toRegionId] with [rotation]
     * @param regionId The old region
     * @param toRegionId The new region
     * @param rotation How much to rotate the region
     */
    override fun set(regionId: Int, toRegionId: Int, rotation: Int) {
        //For each plane in region
        region(regionId, toRegionId) { chunkX, chunkY, toChunkX, toChunkY, plane ->
            //For each 8x8 chunk
            area(8, 8, rotation) { fromX, fromY, toX, toY ->
                //Set the chunk with rotation applied to position
                set(chunkX + fromX, chunkY + fromY, plane, toChunkX + toX, toChunkY + toY, plane, rotation)
            }
        }
    }

    /**
     * Clears the chunk at [chunkX], [chunkY], [plane]
     *
     * @param chunkX The x position of the chunk to remove
     * @param chunkY The y position of the chunk to remove
     * @param plane The z position of the chunk to remove
     */
    override fun clear(chunkX: Int, chunkY: Int, plane: Int) {
        val position = toChunkPosition(chunkX, chunkY, plane)
        if(!clear.contains(position)) {
            clear.add(position)
        }
    }

    /**
     * Clears an area of [width]x[height] chunks
     *
     * @param chunkX The x position of the chunk to replace
     * @param chunkY The y position of the chunk to replace
     * @param plane The z position of the chunk to replace
     * @param width The number of chunks to replace on the x axis
     * @param height The number of chunks to replace on the y axis
     */
    override fun clear(chunkX: Int, chunkY: Int, plane: Int, width: Int, height: Int) {
        area(width, height) { x, y ->
            clear(chunkX + x, chunkY + y, plane)
        }
    }

    /**
     * Clears the whole region
     */
    override fun clear(regionId: Int) {
        val chunkX = (regionId shr 8) * 8
        val chunkY = (regionId and 0xff) * 8
        //For each plane in region
        planes { plane ->
            area(8, 8) { x, y ->
                clear(chunkX + x, chunkY + y, plane)
            }
        }
    }


    /**
     * Reset the chunk at [chunkX], [chunkY], [plane]
     *
     * @param chunkX The x position of the chunk to remove
     * @param chunkY The y position of the chunk to remove
     * @param plane The z position of the chunk to remove
     */
    override fun reset(chunkX: Int, chunkY: Int, plane: Int) {
        set(chunkX, chunkY, plane, chunkX, chunkY, plane, 0)
    }

    /**
     * Resets an area of [width]x[height] to it's original chunks
     *
     * @param chunkX The x position of the chunk to replace
     * @param chunkY The y position of the chunk to replace
     * @param plane The z position of the chunk to replace
     * @param width The number of chunks to replace on the x axis
     * @param height The number of chunks to replace on the y axis
     */
    override fun reset(chunkX: Int, chunkY: Int, plane: Int, width: Int, height: Int) {
        area(width, height) { x, y ->
            reset(chunkX + x, chunkY + y, plane)
        }
    }

    /**
     * Resets the whole region
     */
    override fun reset(regionId: Int) {
        //Clear any changes made so far
        set.clear()
        clear.clear()
        //Reset all chunks
        val chunkX = (regionId shr 8) * 8
        val chunkY = (regionId and 0xff) * 8
        //For each plane in region
        planes { plane ->
            //For each chunk
            area(8, 8) { x, y ->
                //Reset
                reset(chunkX + x, chunkY + y, plane)
            }
        }
    }

    override fun load(regionId: Int) {
        val entityId = regions?.getEntityId(regionId) ?: return
        val dynamic = dynamicMapper.get(entityId) ?: return

        dynamic.regionData.forEach { position, chunk ->
            set[position] = chunk
        }
    }

    /**
     * Applies the changes to the region [regionId]
     * @param regionId The region to apply the changes too
     * @return Success
     */
    override fun build(regionId: Int): Boolean {
        val entityId = regions?.getEntityId(regionId) ?: return false
        val dynamic = dynamicMapper.create(entityId)

        //For all chunks which need clearing and exist
        clear.filter { dynamic.regionData.containsKey(it) }.forEach { position ->
            //Remove the chunk
            dynamic.regionData.remove(position)
            //Flag the chunk to be reloaded
            dynamic.reloads.add(position)
        }

        //For all chunks that need setting
        for ((position, chunk) in set) {
            //If the chunk hasn't changed
            if (dynamic.regionData.containsKey(position) && dynamic.regionData[position] == chunk) {
                continue//We don't need to reload
            }

            //Set the new chunk
            dynamic.regionData[position] = chunk
            //Flag the chunk for reload
            dynamic.reloads.add(position)
        }

        //Check for dynamic region deactivate
        checkReset(entityId)

        //If needs reloading, reload clipping
        if (dynamic.reloads.isNotEmpty() && loadedMapper.has(entityId)) {
            loadedMapper.remove(entityId)
            loadingMapper.create(entityId)
        }

        //Reset builder
        reset()
        return true
    }

    /**
     * Resets the builder
     */
    override fun reset() {
        set.clear()
        clear.clear()
    }

    /**
     * If every chunk is reset it returns the region to static
     */
    private fun checkReset(entityId: Int) {
        val dynamic = dynamicMapper.get(entityId)
        val region = regionMapper.get(entityId)
        val chunkX = region.chunkX
        val chunkY = region.chunkY
        //For each plane in region
        planes { plane ->
            //For each chunk
            area(8, 8) { x, y ->
                if (dynamic.regionData[toChunkPosition(chunkX + x, chunkY + y, plane)] != toRotatedChunkPosition(chunkX + x, chunkY + y, plane, 0)) {
                    return
                }
            }
        }
        dynamicMapper.remove(entityId)
    }

    companion object {

        inline fun forChunks(rangeX: IntRange, rangeY: IntRange, rangeZ: IntRange, action: (Int, Int, Int) -> Unit) {
            for (plane in rangeZ) {
                for (chunkX in rangeX) {
                    for (chunkY in rangeY) {
                        action.invoke(chunkX, chunkY, plane)
                    }
                }
            }
        }

        inline fun forChunks(rangeX: IntRange, rangeY: IntRange, action: (Int, Int) -> Unit) {
            for (chunkX in rangeX) {
                for (chunkY in rangeY) {
                    action.invoke(chunkX, chunkY)
                }
            }
        }

        inline fun area(width: Int, height: Int, rotation: Int, action: (Int, Int, Int, Int) -> Unit) {
            for (x in 0 until width) {
                for (y in 0 until height) {
                    val fromX = when (rotation) {
                        1 -> y
                        3 -> height - 1 - y
                        else -> x
                    }
                    val fromY = when (rotation) {
                        1 -> width - 1 - x
                        3 -> x
                        else -> y
                    }

                    val toX = if (rotation == 2) width - 1 - x else x
                    val toY = if (rotation == 2) height - 1 - y else y

                    action.invoke(fromX, fromY, toX, toY)
                }
            }
        }

        inline fun area(width: Int, height: Int, action: (Int, Int) -> Unit) {
            for (x in 0 until width) {
                for (y in 0 until height) {
                    action.invoke(x, y)
                }
            }
        }

        inline fun planes(action: (Int) -> Unit) {
            for (plane in PLANE_RANGE) {
                action.invoke(plane)
            }
        }

        inline fun region(regionId: Int, toRegionId: Int, action: (Int, Int, Int, Int, Int) -> Unit) {
            val fromChunkX = (regionId shr 8) * 8
            val fromChunkY = (regionId and 0xff) * 8
            val toChunkX = (toRegionId shr 8) * 8
            val toChunkY = (toRegionId and 0xff) * 8
            planes { plane ->
                action(fromChunkX, fromChunkY, toChunkX, toChunkY, plane)
            }
        }
    }
}