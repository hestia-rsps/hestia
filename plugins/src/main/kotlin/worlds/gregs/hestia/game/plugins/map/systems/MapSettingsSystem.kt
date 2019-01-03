package worlds.gregs.hestia.game.plugins.map.systems

import com.artemis.annotations.Wire
import world.gregs.hestia.core.network.packets.Packet
import worlds.gregs.hestia.api.map.ClippingMasks
import worlds.gregs.hestia.api.map.MapSettings
import worlds.gregs.hestia.game.map.Flags
import worlds.gregs.hestia.game.map.Flags.BLOCKED_TILE
import worlds.gregs.hestia.game.map.Flags.BRIDGE_TILE
import worlds.gregs.hestia.game.plugins.region.systems.RegionBuilderSystem.Companion.forChunks
import worlds.gregs.hestia.game.plugins.region.systems.load.ChunkRotationSystem
import worlds.gregs.hestia.game.map.MapConstants.PLANE_RANGE
import worlds.gregs.hestia.game.map.MapConstants.REGION_PLANES
import worlds.gregs.hestia.game.map.MapConstants.REGION_RANGE
import worlds.gregs.hestia.game.map.MapConstants.REGION_SIZE

/**
 * MapSettingsSystem
 * Adds clipping masks using [ClippingMaskSystem] from the [worlds.gregs.hestia.game.plugins.region.systems.load.RegionFileSystem] data
 */
@Wire(failOnNull = false)
class MapSettingsSystem : MapSettings() {

    private var masks: ClippingMasks? = null
    private lateinit var chunk: ChunkRotationSystem

    /**
     * Loads the region tile settings
     * @param mapContainerData The map file data
     * @return The decoded height settings
     */
    override fun load(mapContainerData: ByteArray): Array<Array<ByteArray>> {
        val mapSettings = Array(REGION_PLANES) { Array(REGION_SIZE) { ByteArray(REGION_SIZE) } }
        val mapStream = Packet(mapContainerData)
        //For every region tile
        forChunks(REGION_RANGE, REGION_RANGE, PLANE_RANGE) { localX, localY, plane ->
            var value = 2
            while (value > 1) {
                value = mapStream.readUnsignedByte()
                when {
                    value <= 49 -> mapStream.readByte()
                    value <= 81 -> mapSettings[plane][localX][localY] = (value - 49).toByte()
                }
            }
        }
        return mapSettings
    }

    /**
     * Adds masks to clipping based on the settings provided
     * @param entityId The regions entity id
     * @param settings The tile settings
     * @param rotation The chunks rotation
     * @param chunkX The chunks x coordinate
     * @param chunkY The chunks y coordinate
     * @param chunkPlane The chunks plane coordinate
     */
    override fun apply(entityId: Int, settings: Array<Array<ByteArray>>, rotation: Int?, chunkX: Int?, chunkY: Int?, chunkPlane: Int?) {
        if (rotation == null) {
            //Static region
            applySettings(settings, REGION_RANGE, REGION_RANGE, PLANE_RANGE) { localX, localY, plane ->
                //Add mask
                masks?.addMask(entityId, localX, localY, plane, Flags.FLOOR_BLOCKS_WALK)
            }
        } else if (chunkX != null && chunkY != null && chunkPlane != null) {
            //Dynamic region
            //Add settings for only the chunk
            applySettings(settings, chunkX until chunkX + 8, chunkY until chunkY + 8, chunkPlane..chunkPlane) { localX, localY, plane ->
                //Calculate new position after rotation
                val newX = chunk.translateX(localX and 0x7, localY and 0x7, rotation)
                val newY = chunk.translateY(localX and 0x7, localY and 0x7, rotation)
                //Add mask
                masks?.addMask(entityId, chunkX or newX, chunkY or newY, plane, Flags.FLOOR_BLOCKS_WALK)
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
}