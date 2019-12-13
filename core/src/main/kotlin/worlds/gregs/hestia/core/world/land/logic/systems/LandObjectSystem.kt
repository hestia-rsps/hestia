package worlds.gregs.hestia.core.world.land.logic.systems

import com.artemis.annotations.Wire
import net.mostlyoriginal.api.event.common.EventSystem
import world.gregs.hestia.core.network.codec.packet.PacketReader
import worlds.gregs.hestia.core.entity.`object`.model.events.CreateObject
import worlds.gregs.hestia.core.world.collision.model.Flags.BRIDGE_TILE
import worlds.gregs.hestia.core.world.land.api.Land
import worlds.gregs.hestia.core.world.land.api.LandObjects
import worlds.gregs.hestia.core.world.map.model.MapConstants.PLANE_RANGE
import worlds.gregs.hestia.core.world.map.model.MapConstants.isOutOfBounds
import worlds.gregs.hestia.core.world.region.logic.systems.load.ChunkRotationSystem
import worlds.gregs.hestia.core.world.region.logic.systems.load.RegionFileSystem
import worlds.gregs.hestia.service.cache.systems.ObjectDefinitionSystem

/**
 * MapObjectSystem
 * Adds region objects using [Land] from the [RegionFileSystem] data
 */
@Wire(failOnNull = false)
class LandObjectSystem : LandObjects() {

    private lateinit var objectDefinitions: ObjectDefinitionSystem
    private lateinit var chunk: ChunkRotationSystem
    private lateinit var es: EventSystem

    override fun load(entityId: Int, x: Int, y: Int, landContainerData: ByteArray, settings: Array<Array<ByteArray>>?, rotation: Int?, chunkX: Int?, chunkY: Int?, chunkPlane: Int?) {
        val landStream = PacketReader(landContainerData)
        //Loop around all the data
        var objectId = -1
        var skip: Int
        while (true) {
            skip = landStream.readLargeSmart()
            if (skip == 0) {
                break
            }
            objectId += skip
            var location = 0
            var data: Int
            while (true) {
                data = landStream.readSmart()
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
                var objectRotation = objectData and 0x3

                //Check that the object is within this region
                if (isOutOfBounds(localX, localY)) {
                    continue
                }

                //If it's a bridge decrease the plane
                if (settings != null && settings[1][localX][localY].toInt() and BRIDGE_TILE == BRIDGE_TILE) {
                    plane--
                }

                //Make sure that plane is valid
                if (plane !in PLANE_RANGE) {
                    continue
                }

                //If it's a dynamic region
                if (rotation != null && chunkX != null && chunkY != null) {
                    //Make sure we're only adding objects to this chunk
                    //TODO Performance improvements could be made for loading multiple chunks from the same region at once
                    if (localX !in chunkX..chunkX + 8 || localY !in chunkY..chunkY + 8 || plane != chunkPlane) {
                        continue
                    }
                    //Get the object definition
                    val definition = objectDefinitions.get(objectId)
                    //Calculate new position after rotation
                    val newX = chunk.translateX(localX and 0x7, localY and 0x7, rotation, definition.sizeX, definition.sizeY, objectRotation)
                    val newY = chunk.translateY(localX and 0x7, localY and 0x7, rotation, definition.sizeX, definition.sizeY, objectRotation)
                    //Add the new local (chunk) offset and remove the old local offset
                    localX += newX - (localX % 8)
                    localY += newY - (localY % 8)
                    //Combine rotations
                    objectRotation = (rotation + objectRotation) and 0x3
                }

                //Create the object
                es.dispatch(CreateObject(objectId, localX + x, localY + y, plane, type, objectRotation))
            }
        }
    }
}