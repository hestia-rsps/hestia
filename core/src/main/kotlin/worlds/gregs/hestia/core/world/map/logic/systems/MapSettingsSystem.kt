package worlds.gregs.hestia.core.world.map.logic.systems

import com.artemis.annotations.Wire
import io.netty.buffer.Unpooled
import worlds.gregs.hestia.core.world.collision.model.CollisionFlag
import worlds.gregs.hestia.core.world.map.api.MapCollisionFlags
import worlds.gregs.hestia.core.world.map.api.MapSettings
import worlds.gregs.hestia.core.world.map.model.MapConstants.PLANE_RANGE
import worlds.gregs.hestia.core.world.map.model.MapConstants.REGION_PLANES
import worlds.gregs.hestia.core.world.map.model.MapConstants.REGION_RANGE
import worlds.gregs.hestia.core.world.map.model.MapConstants.REGION_SIZE
import worlds.gregs.hestia.core.world.region.logic.systems.RegionBuilderSystem.Companion.forChunks
import worlds.gregs.hestia.core.world.region.logic.systems.load.ChunkRotationSystem
import worlds.gregs.hestia.core.world.region.logic.systems.load.RegionFileSystem

/**
 * MapSettingsSystem
 * Adds collision flags using [MapCollisionFlagSystem] from the [RegionFileSystem] data
 */
@Wire(failOnNull = false)
class MapSettingsSystem : MapSettings() {

    private var flags: MapCollisionFlags? = null
    private lateinit var chunk: ChunkRotationSystem

    /**
     * Loads the region tile settings
     * @param mapContainerData The map file data
     * @return The decoded height settings
     */
    override fun load(mapContainerData: ByteArray): Array<Array<ByteArray>> {
        val mapSettings = Array(REGION_PLANES) { Array(REGION_SIZE) { ByteArray(REGION_SIZE) } }
        val buffer = Unpooled.wrappedBuffer(mapContainerData)
        var config: Int
        //For every region tile
        forChunks(REGION_RANGE, REGION_RANGE, PLANE_RANGE) { localX, localY, plane ->
            loop@ while (true) {
                config = if(buffer.isReadable(1)) buffer.readUnsignedByte().toInt() else 0
                when {
                    config == 0 -> break@loop
                    config == 1 -> {
                        if(buffer.isReadable(1)) buffer.readByte()
                        break@loop
                    }
                    config <= 49 -> if(buffer.isReadable(1)) buffer.readByte()
                    config <= 81 -> mapSettings[plane][localX][localY] = (config - 49).toByte()
                }
            }
        }
        return mapSettings
    }

    override fun apply(regionX: Int, regionY: Int, settings: Array<Array<ByteArray>>, rotation: Int?, chunkX: Int?, chunkY: Int?, chunkPlane: Int?) {
        if (rotation == null) {
            //Static region
            applySettings(settings, REGION_RANGE, REGION_RANGE, PLANE_RANGE) { localX, localY, plane ->
                //Add flag
                flags?.addFlag(regionX + localX, regionY + localY, plane, CollisionFlag.FLOOR)
            }
        } else if (chunkX != null && chunkY != null && chunkPlane != null) {
            //Dynamic region
            //Add settings for only the chunk
            applySettings(settings, chunkX until chunkX + 8, chunkY until chunkY + 8, chunkPlane..chunkPlane) { localX, localY, plane ->
                //Calculate new position after rotation
                val newX = chunk.translateX(localX and 0x7, localY and 0x7, rotation)
                val newY = chunk.translateY(localX and 0x7, localY and 0x7, rotation)
                //Add flag
                flags?.addFlag(regionX + chunkX or newX, regionY + chunkY or newY, plane, CollisionFlag.FLOOR)
            }
        }
    }

    /**
     * Applies [action] to all tiles in the region which are blocked (but not a bridge)
     * @param settings The tile settings
     * @param horizontal The x range to iterate
     * @param vertical The y range to iterate
     * @param planes The plane range to iterate
     * @param action Action to apply
     */
    private fun applySettings(settings: Array<Array<ByteArray>>, horizontal: IntRange, vertical: IntRange, planes: IntRange, action: (Int, Int, Int) -> Unit) {
        //Check all tiles in specified range
        for (plane in planes) {
            for (localX in horizontal) {
                for (localY in vertical) {
                    //If blocked but not by a bridge
                    if (settings[plane][localX][localY].toInt() and BLOCKED_TILE == BLOCKED_TILE && settings[1][localX][localY].toInt() and BRIDGE_TILE != BRIDGE_TILE) {
                        action(localX, localY, plane)
                    }
                }
            }
        }
    }

    companion object {
        const val BLOCKED_TILE = 0x1
        const val BRIDGE_TILE = 0x2
    }
}