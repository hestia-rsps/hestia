package worlds.gregs.hestia.game.plugins.region.systems.load

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.system.core.PassiveSystem
import world.gregs.hestia.core.network.packets.Packet
import worlds.gregs.hestia.game.map.Flags
import worlds.gregs.hestia.game.plugins.region.components.Clipping
import worlds.gregs.hestia.game.plugins.region.systems.RegionBuilder.Companion.forChunks
import worlds.gregs.hestia.game.plugins.region.systems.change.ClippingMaskSystem
import worlds.gregs.hestia.game.plugins.region.systems.change.RegionObjectSystem
import worlds.gregs.hestia.game.plugins.region.systems.change.RegionObjectSystem.Companion.PLANE_RANGE
import worlds.gregs.hestia.game.plugins.region.systems.change.RegionObjectSystem.Companion.REGION_RANGE

/**
 * MapObjectSystem
 * Adds clipping masks using [ClippingMaskSystem] from the [MapFileSystem] data
 */
class MapClippingSystem : PassiveSystem() {

    private lateinit var clippingMapper: ComponentMapper<Clipping>
    private lateinit var rms: ClippingMaskSystem
    private lateinit var chunk: ChunkRotationSystem

    /**
     * Loads the region tile height settings
     * @param mapContainerData The map file data
     * @return The decoded height settings
     */
    fun loadSettings(mapContainerData: ByteArray): Array<Array<ByteArray>> {
        val mapSettings = Array(RegionObjectSystem.REGION_PLANES) { Array(RegionObjectSystem.REGION_SIZE) { ByteArray(RegionObjectSystem.REGION_SIZE) } }
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
     */
    fun applySettings(entityId: Int, settings: Array<Array<ByteArray>>, rotation: Int? = null, chunkX: Int? = null, chunkY: Int? = null, regionPlane: Int? = null) {
        val clipping = clippingMapper.create(entityId)

        if (rotation == null) {
            //Static region
            applySettings(settings, RegionObjectSystem.REGION_RANGE, RegionObjectSystem.REGION_RANGE, RegionObjectSystem.PLANE_RANGE) { localX, localY, plane ->
                //Add mask
                rms.changeMask(entityId, clipping, plane, localX, localY, Flags.FLOOR_BLOCKS_WALK, ClippingMaskSystem.ADD_MASK)
            }
        } else if (chunkX != null && chunkY != null && regionPlane != null) {
            //Dynamic region
            //Add settings for only the chunk
            applySettings(settings, chunkX until chunkX + 8, chunkY until chunkY + 8, regionPlane..regionPlane) { localX, localY, plane ->
                //Calculate new position after rotation
                val newX = chunk.translateX(localX and 0x7, localY and 0x7, rotation)
                val newY = chunk.translateY(localX and 0x7, localY and 0x7, rotation)
                //Add mask
                rms.changeMask(entityId, clipping, plane, chunkX or newX, chunkY or newY, Flags.FLOOR_BLOCKS_WALK, ClippingMaskSystem.ADD_MASK)
            }
        }
    }

    private fun applySettings(settings: Array<Array<ByteArray>>, horizontal: IntRange, vertical: IntRange, planes: IntRange, apply: (Int, Int, Int) -> Unit) {
        //Check all tiles in specified range
        for (plane in planes) {
            for (localX in horizontal) {
                for (localY in vertical) {
                    //If blocked but not by a bridge
                    if (settings[plane][localX][localY].toInt() and BLOCKED_TILE == BLOCKED_TILE && settings[1][localX][localY].toInt() and BRIDGE_TILE != BRIDGE_TILE) {
                        apply(localX, localY, plane)
                    }
                }
            }
        }
    }

    companion object {
        private const val BLOCKED_TILE = 0x1
        private const val BRIDGE_TILE = 0x2
    }
}