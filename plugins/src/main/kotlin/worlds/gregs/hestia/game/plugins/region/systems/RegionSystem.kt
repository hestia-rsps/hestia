package worlds.gregs.hestia.game.plugins.region.systems

import com.artemis.ComponentMapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.mostlyoriginal.api.event.common.EventSystem
import org.slf4j.LoggerFactory
import world.gregs.hestia.core.network.packets.Packet
import worlds.gregs.hestia.game.events.CreateRegion
import worlds.gregs.hestia.game.map.Flags
import worlds.gregs.hestia.game.map.GameObject
import worlds.gregs.hestia.game.plugins.core.systems.cache.CacheSystem
import worlds.gregs.hestia.game.plugins.core.systems.extensions.SubscriptionSystem
import worlds.gregs.hestia.game.plugins.region.components.Clipping
import worlds.gregs.hestia.game.plugins.region.components.Objects
import worlds.gregs.hestia.game.plugins.region.components.ProjectileClipping
import worlds.gregs.hestia.game.plugins.region.components.Region
import worlds.gregs.hestia.game.plugins.region.systems.RegionObjectSystem.Companion.REGION_PLANES
import worlds.gregs.hestia.game.plugins.region.systems.RegionObjectSystem.Companion.REGION_SIZE
import worlds.gregs.hestia.game.plugins.region.systems.RegionObjectSystem.Companion.isOutOfBounds
import worlds.gregs.hestia.services.Aspect

class RegionSystem : SubscriptionSystem(Aspect.all(Region::class)) {

    private val list = HashMap<Int, Int>()
    private lateinit var es: EventSystem
    private lateinit var ros: RegionObjectSystem
    private lateinit var rms: RegionMapSystem
    private lateinit var cache: CacheSystem
    private lateinit var regionMapper: ComponentMapper<Region>
    private lateinit var clippingMapper: ComponentMapper<Clipping>
    private lateinit var projectileClippingMapper: ComponentMapper<ProjectileClipping>
    private lateinit var objectsMapper: ComponentMapper<Objects>
    private val logger = LoggerFactory.getLogger(RegionSystem::class.java)

    override fun inserted(entityId: Int) {
        val region = regionMapper.get(entityId)
        list[region.id] = entityId
        if (region.loadStage == 0) {
            region.loadStage = 1
            GlobalScope.launch {
                load(entityId, region.id)
                region.loadStage = 2
            }
        }
    }

    fun getEntity(regionId: Int): Int? {
        return list[regionId]
    }

    override fun removed(entityId: Int) {
        val region = regionMapper.get(entityId)
        list.remove(region.id, entityId)
    }

    /**
     * Loads region clipping if hasn't been loaded
     * @param regionId
     */
    fun load(regionId: Int) {
        if (list.containsKey(regionId)) {
            return
        }

        es.dispatch(CreateRegion(regionId))
    }


    /**
     * Unloads region resources if the region is loaded
     * @param regionId
     */
    fun unload(regionId: Int) {
        val entityId = getEntity(regionId) ?: return
        val region = regionMapper.get(entityId)
        if (region.loaded()) {
            objectsMapper.get(entityId).objects = null
            clippingMapper.remove(entityId)
            projectileClippingMapper.remove(entityId)
            region.loadStage = 0
        }
    }

    private fun load(entityId: Int, regionId: Int) {
        println("Load $regionId")
        val regionX = regionId shr 8
        val regionY = regionId and 0xff
        val x = regionX * 64
        val y = regionY * 64
        val index = cache.getIndex(5)
        val landIndex = index.getArchiveId("l${regionX}_$regionY")
        val mapIndex = index.getArchiveId("m${regionX}_$regionY")
        if (landIndex == -1 || mapIndex == -1) {
            return
        }

        val landContainerData = index.getFile(landIndex)
        val mapContainerData = index.getFile(mapIndex)

        var settings: Array<Array<ByteArray>>? = null
        if (mapContainerData != null) {
            settings = loadSettings(entityId, mapContainerData)
        }

        if (landContainerData != null) {
            loadObjects(entityId, x, y, landContainerData, settings)
        }

        if (landContainerData == null) {
            logger.warn("Missing xteas for region $regionId.")
        }
    }

    private fun loadSettings(entityId: Int, mapContainerData: ByteArray): Array<Array<ByteArray>> {
        val mapSettings = Array(REGION_PLANES) { Array(REGION_SIZE) { ByteArray(REGION_SIZE) } }
        val mapStream = Packet(mapContainerData)
        forTiles { localX, localY, plane ->
            var value = 2
            while (value > 1) {
                value = mapStream.readUnsignedByte()
                when {
                    value <= 49 -> mapStream.readByte()
                    value <= 81 -> mapSettings[plane][localX][localY] = (value - 49).toByte()
                }
            }
        }
        val clippingMap = clippingMapper.create(entityId)
        forTiles { localX, localY, plane ->
            if (mapSettings[plane][localX][localY].toInt() and BLOCKED_TILE == BLOCKED_TILE && mapSettings[1][localX][localY].toInt() and BRIDGE_TILE != BRIDGE_TILE) {
                rms.changeMask(entityId, clippingMap, plane, localX, localY, Flags.FLOOR_BLOCKS_WALK, RegionMapSystem.ADD_MASK)
            }
        }
        return mapSettings
    }

    private fun loadObjects(entityId: Int, x: Int, y: Int, landContainerData: ByteArray, settings: Array<Array<ByteArray>>?) {
        val planeRange = 0 until REGION_PLANES
        val landStream = Packet(landContainerData)
        var objectId = -1
        var skip: Int
        while (true) {
            skip = landStream.readSmart2()
            if (skip == 0) {
                break
            }
            objectId += skip
            var location = 0
            var hash: Int
            while (true) {
                hash = landStream.readUnsignedSmart()
                if (hash == 0) {
                    break
                }
                location += hash - 1
                val localX = location shr 6 and 0x3f
                val localY = location and 0x3f
                var plane = location shr 12
                val objectData = landStream.readUnsignedByte()
                val type = objectData shr 2
                val rotation = objectData and 0x3

                if (isOutOfBounds(localX, localY)) {
                    continue
                }

                if (settings != null && settings[1][localX][localY].toInt() and BRIDGE_TILE == BRIDGE_TILE) {
                    plane--
                }

                if (plane !in planeRange) {
                    continue
                }

                ros.addObject(entityId, GameObject(objectId, type, rotation, localX + x, localY + y, plane), plane, localX, localY)
            }
        }
    }

    private fun forTiles(action: (Int, Int, Int) -> Unit) {
        for (plane in 0 until REGION_PLANES) {
            for (localX in 0 until REGION_SIZE) {
                for (localY in 0 until REGION_SIZE) {
                    action(localX, localY, plane)
                }
            }
        }
    }

    companion object {
        private const val BLOCKED_TILE = 0x1
        private const val BRIDGE_TILE = 0x2
    }
}

fun Region.loaded(): Boolean {
    return loadStage == 2
}