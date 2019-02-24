package worlds.gregs.hestia.game.map

import io.netty.buffer.Unpooled
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import world.gregs.hestia.core.cache.Cache
import world.gregs.hestia.core.cache.CacheStore
import world.gregs.hestia.core.network.codec.packet.PacketReader

internal class MapIndexDecoderTest {

    private lateinit var cache: Cache

    @BeforeEach
    fun setUp() {
        cache = CacheStore("../../hestia/data/cache/")
    }

    @Test
    fun decode() {
        val index = cache.getIndex(5)

        val range = 0..162

        val list = HashMap<Int, Map<Int, Int>>()
        var objectCount = 0
        var regionCount = 0
        var emptyRegionCount = 0
        for (x in range) {
            for (y in range) {
                val mapIndex = index.getArchiveId("m${x}_$y")
                val landIndex = index.getArchiveId("l${x}_$y")
                if (mapIndex != -1 && landIndex != -1) {
                    regionCount++
                    val regionId = (x shl 8) + y
                    val landContainerData = if (landIndex == -1) null else index.getFile(landIndex, 0, null)
                    val mapContainerData = if (mapIndex == -1) null else index.getFile(mapIndex, 0)
                    val mapSettings = if (mapContainerData == null) null else Array(4) { Array(64) { ByteArray(64) } }
                    if (mapContainerData != null) {
                        val mapStream = PacketReader(mapContainerData)
                        for (plane in 0..3) {
                            for (x in 0..63) {
                                for (y in 0..63) {
                                    while (true) {
                                        val value = mapStream.readUnsignedByte()
                                        if (value == 0) {
                                            break
                                        } else if (value == 1) {
                                            mapStream.readByte()
                                            break
                                        } else if (value <= 49) {
                                            mapStream.readByte()

                                        } else if (value <= 81) {
                                            mapSettings!![plane][x][y] = (value - 49).toByte()
                                        }
                                    }
                                }
                            }
                        }
                        for (plane in 0..3) {
                            for (x in 0..63) {
                                for (y in 0..63) {
                                    if (mapSettings!![plane][x][y].toInt() and 0x1 == 1 && mapSettings[1][x][y].toInt() and 2 != 2) {
//                                        region.getMap(false).changeMask(plane, x, y, Flags.FLOOR_BLOCKS_WALK, RegionMap.ADD_MASK)
                                    }
                                }
                            }
                        }
                    }
                    if (landContainerData != null) {
                        val map = HashMap<Int, Int>()
                        val landStream = PacketReader(landContainerData)
                        var objectId = -1
                        var incr: Int
                        while (true) {
                            incr = landStream.readLargeSmart()
                            if (incr == 0)
                                break
                            objectId += incr
                            var location = 0
                            var incr2: Int
                            while (true) {
                                incr2 = landStream.readSmart()
                                if (incr2 == 0)
                                    break
                                location += incr2 - 1
                                val localX = location shr 6 and 0x3f
                                val localY = location and 0x3f
                                val plane = location shr 12
                                val objectData = landStream.readUnsignedByte()
                                val type = objectData shr 2
                                val rotation = objectData and 0x3
                                if (localX < 0 || localX >= 64 || localY < 0 || localY >= 64) {
                                    continue
                                }
                                var objectPlane = plane
                                if (mapSettings != null && mapSettings[1][localX][localY].toInt() and 2 == 2) {
                                    objectPlane--
                                }
                                if (plane < 0 || plane >= 4 && !(objectPlane < 0 || objectPlane >= 4)) {
                                    println("Necessary!?")
                                }
                                if (objectPlane < 0 || objectPlane >= 4 || plane < 0 || plane >= 4) {
                                    continue
                                }
                                val chunkX = (localX + x) and 0x7
                                val chunkY = (localY + y) and 0x7

                                val chunkId = (chunkX shl 8) + chunkY
                                if (map.containsKey(chunkId)) {
                                    map[chunkId] = map[chunkId]!! + 1
                                } else {
                                    map[chunkId] = 1
                                }
                                objectCount++
//                                region.objects.addObject(WorldObject(objectId, type, rotation, localX + regionX, localY + regionY, plane), objectPlane, localX, localY)
                            }
                        }
                        list[regionId] = map.toList()
                                .sortedBy { (_, value) -> value }
                                .toMap()
                    }
                } else {
                    emptyRegionCount++
                }
            }
        }
        println("Loaded $regionCount regions with $objectCount objects. $emptyRegionCount empty regions")
        println(list.toList())
    }

    @Test
    fun test() {
        val index = cache.getIndex(5)
        val range = 0..162
        var counter = 0
        for (x in range) {
            for (y in range) {
                val id = index.getArchiveId("n${x}_$y")
                if (id != -1) {
                    val npcSpawnsContainerData = cache.getIndex(5).getFile(id, 0, null)
                    println("Found: n${x}_$y ${x * 64} ${y * 64}")
                    if (npcSpawnsContainerData != null) {
                        val buffer = Unpooled.wrappedBuffer(npcSpawnsContainerData)
                        while (buffer.readableBytes() > 0) {
                            val hash = buffer.readUnsignedShort()
                            val npcId = buffer.readUnsignedShort()
                            val plane = hash shr 758085070
                            val localX = 0x1f92 and hash shr -585992921
                            val actualX = (x * 64) + localX
                            val localY = 0x3f and hash
                            val actualY = (y * 64) + localY
                            println("Npc: $npcId $plane $localX $localY $actualX $actualY")
                            counter++
                        }
                    }
                }
            }
        }
        println("Found $counter npc spawns")
    }
}