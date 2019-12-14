package worlds.gregs.hestia.core.world.region.api

import net.mostlyoriginal.api.system.core.PassiveSystem

/**
 * RegionBuilder
 * Toolset to dynamically modify region maps
 * Uses exact chunk coordinates, not local
 */
abstract class RegionBuilder : PassiveSystem() {

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
    abstract fun set(chunkX: Int, chunkY: Int, plane: Int, toChunkX: Int, toChunkY: Int, toPlane: Int, rotation: Int = 0)

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
    abstract fun set(chunkX: Int, chunkY: Int, plane: Int, toChunkX: Int, toChunkY: Int, toPlane: Int, width: Int, height: Int, rotation: Int)

    /**
     * Set's the whole region [regionId] to [toRegionId] with [rotation]
     * @param regionId The old region
     * @param toRegionId The new region
     * @param rotation How much to rotate the region
     */
    abstract fun set(regionId: Int, toRegionId: Int, rotation: Int = 0)

    /**
     * Clears the chunk at [chunkX], [chunkY], [plane]
     *
     * @param chunkX The x position of the chunk to remove
     * @param chunkY The y position of the chunk to remove
     * @param plane The z position of the chunk to remove
     */
    abstract fun clear(chunkX: Int, chunkY: Int, plane: Int)

    /**
     * Clears an area of [width]x[height] chunks
     *
     * @param chunkX The x position of the chunk to replace
     * @param chunkY The y position of the chunk to replace
     * @param plane The z position of the chunk to replace
     * @param width The number of chunks to replace on the x axis
     * @param height The number of chunks to replace on the y axis
     */
    abstract fun clear(chunkX: Int, chunkY: Int, plane: Int, width: Int, height: Int)

    /**
     * Clears the whole region
     */
    abstract fun clear(regionId: Int)

    /**
     * Reset the chunk at [chunkX], [chunkY], [plane]
     *
     * @param chunkX The x position of the chunk to remove
     * @param chunkY The y position of the chunk to remove
     * @param plane The z position of the chunk to remove
     */
    abstract fun reset(chunkX: Int, chunkY: Int, plane: Int)

    /**
     * Resets an area of [width]x[height] to it's original chunks
     *
     * @param chunkX The x position of the chunk to replace
     * @param chunkY The y position of the chunk to replace
     * @param plane The z position of the chunk to replace
     * @param width The number of chunks to replace on the x axis
     * @param height The number of chunks to replace on the y axis
     */
    abstract fun reset(chunkX: Int, chunkY: Int, plane: Int, width: Int, height: Int)

    /**
     * Resets the whole region
     */
    abstract fun reset(regionId: Int)

    /**
     * Loads existing region builder changes
     * @param regionId The region to load changes from
     */
    abstract fun load(regionId: Int)

    /**
     * Applies the changes to the region [regionId]
     * @param regionId The region to apply the changes too
     * @return Success
     */
    abstract fun build(regionId: Int): Boolean

    /**
     * Resets the builder
     */
    abstract fun reset()
}