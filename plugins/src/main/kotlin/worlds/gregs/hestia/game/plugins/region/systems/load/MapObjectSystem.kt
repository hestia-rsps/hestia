package worlds.gregs.hestia.game.plugins.region.systems.load

import net.mostlyoriginal.api.system.core.PassiveSystem
import world.gregs.hestia.core.network.packets.Packet
import worlds.gregs.hestia.game.map.GameObject
import worlds.gregs.hestia.game.plugins.core.systems.cache.ObjectDefinitionSystem
import worlds.gregs.hestia.game.plugins.region.systems.change.RegionObjectSystem

/**
 * MapObjectSystem
 * Adds region objects using [RegionObjectSystem] from the [MapFileSystem] data
 */
class MapObjectSystem : PassiveSystem() {

    private lateinit var ros: RegionObjectSystem
    private lateinit var objectDefinitions: ObjectDefinitionSystem
    private lateinit var chunk: ChunkRotationSystem

    fun loadObjects(entityId: Int, x: Int, y: Int, landContainerData: ByteArray, settings: Array<Array<ByteArray>>?, regionRotation: Int? = null, chunkX: Int? = null, chunkY: Int? = null, regionPlane: Int? = null) {
        val landStream = Packet(landContainerData)
        //Loop around all the data
        var objectId = -1
        var skip: Int
        while (true) {
            skip = landStream.readSmart2()
            if (skip == 0) {
                break
            }
            objectId += skip
            var location = 0
            var data: Int
            while (true) {
                data = landStream.readUnsignedSmart()
                if (data == 0) {
                    break
                }
                location += data - 1
                //Get the object data
                var localX = location shr 6 and 0x3f
                var localY = location and 0x3f
                var plane = location shr 12
                val objectData = landStream.readUnsignedByte()
                val type = objectData shr 2
                var rotation = objectData and 0x3

                //Check that the object is within this region
                if (RegionObjectSystem.isOutOfBounds(localX, localY)) {
                    continue
                }

                //If it's a bridge decrease the plane
                if (settings != null && settings[1][localX][localY].toInt() and BRIDGE_TILE == BRIDGE_TILE) {
                    plane--
                }

                //Make sure that plane is valid
                if (plane !in RegionObjectSystem.PLANE_RANGE) {
                    continue
                }

                //If it's a dynamic region
                if (regionRotation != null && chunkX != null && chunkY != null) {
                    //Make sure we're only adding objects to this chunk
                    //TODO Performance improvements could be made for loading multiple chunks from the same region at once
                    if (localX !in chunkX..chunkX + 8 || localY !in chunkY..chunkY + 8 || plane != regionPlane) {
                        continue
                    }
                    //Get the object definition
                    val definition = objectDefinitions.get(objectId)
                    //Calculate new position after rotation
                    val newX = chunk.translateX(localX and 0x7, localY and 0x7, regionRotation, definition.sizeX, definition.sizeY, rotation)
                    val newY = chunk.translateY(localX and 0x7, localY and 0x7, regionRotation, definition.sizeX, definition.sizeY, rotation)
                    //Add the new local (chunk) offset and remove the old local offset
                    localX += newX - (localX % 8)
                    localY += newY - (localY % 8)
                    //Combine rotations
                    rotation = (regionRotation + rotation) and 0x3
                }

                //Add the object
                ros.addObject(entityId, GameObject(objectId, type, rotation, localX + x, localY + y, plane), plane, localX, localY)
            }
        }
    }

    companion object {
        private const val BRIDGE_TILE = 0x2
    }
}