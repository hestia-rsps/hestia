package worlds.gregs.hestia.game.plugins.region.systems.load

import com.artemis.ComponentMapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.game.plugins.core.systems.cache.CacheSystem
import worlds.gregs.hestia.game.plugins.core.systems.extensions.SubscriptionSystem
import worlds.gregs.hestia.game.plugins.region.components.*
import worlds.gregs.hestia.game.plugins.region.systems.RegionBuilder.Companion.getRotatedChunkPlane
import worlds.gregs.hestia.game.plugins.region.systems.RegionBuilder.Companion.getRotatedChunkRotation
import worlds.gregs.hestia.game.plugins.region.systems.RegionBuilder.Companion.getRotatedChunkX
import worlds.gregs.hestia.game.plugins.region.systems.RegionBuilder.Companion.getRotatedChunkY
import worlds.gregs.hestia.services.Aspect

/**
 * MapFileSystem
 * Loads region clipping and objects
 * Loads the maps from the cache and passes the data off to [MapClippingSystem] & [MapObjectSystem]
 */
class MapFileSystem : SubscriptionSystem(Aspect.all(Region::class, Loading::class)) {

    private lateinit var cache: CacheSystem
    private lateinit var clipping: MapClippingSystem
    private lateinit var objects: MapObjectSystem
    private lateinit var dynamicMapper: ComponentMapper<Dynamic>
    private lateinit var regionMapper: ComponentMapper<Region>
    private lateinit var loadingMapper: ComponentMapper<Loading>
    private lateinit var loadedMapper: ComponentMapper<Loaded>

    private lateinit var projectileClippingMapper: ComponentMapper<ProjectileClipping>
    private lateinit var clippingMapper: ComponentMapper<Clipping>

    private val logger = LoggerFactory.getLogger(MapFileSystem::class.java)

    override fun inserted(entityId: Int) {
        //Clear any old clipping data
        projectileClippingMapper.remove(entityId)
        clippingMapper.remove(entityId)
        //Load async
        val region = regionMapper.get(entityId)
        GlobalScope.launch {
            loadingMapper.remove(entityId)
            load(entityId, region.id)
            loadedMapper.create(entityId)
        }
    }

    /**
     * Load clipping for region [regionId]
     * @param entityId Entity id of the region
     * @param regionId Location id of the region
     */
    fun load(entityId: Int, regionId: Int) {
        val regionX = regionId shr 8
        val regionY = regionId and 0xff
        //Calculate region coordinates
        val x = regionX * 64
        val y = regionY * 64

        if (!dynamicMapper.has(entityId)) {
            //Load static region
            load(entityId, x, y, regionX, regionY)
        } else {
            //Load dynamic region
            val dynamic = dynamicMapper.get(entityId)
            //For each chunk which need reloading
            dynamic.regionData.filter { dynamic.reloads.contains(it.key) }.forEach { chunk, shift ->
                //Doesn't need reloading anymore
                dynamic.reloads.remove(chunk)
                //Get the data of the chunk we are copying
                val newChunkX = getRotatedChunkX(shift)
                val newChunkY = getRotatedChunkY(shift)
                val plane = getRotatedChunkPlane(shift)
                val rotation = getRotatedChunkRotation(shift)

                //Calculate the location of the new chunks region
                val newRegionX = newChunkX / 8
                val newRegionY = newChunkY / 8

                //Get position of the chunk we are replacing and calculate the local chunk position (in the region) in tiles
                val chunkX = ((chunk shr 14 and 0x3fff) % 8) * 8
                val chunkY = ((chunk and 0x3fff) % 8) * 8

                //Load dynamic region
                load(entityId, x, y, newRegionX, newRegionY, rotation, chunkX, chunkY, plane)
            }
        }
    }

    /**
     * Loads clipping & objects from map files
     */
    private fun load(entityId: Int, x: Int, y: Int, regionX: Int, regionY: Int, rotation: Int? = null, chunkX: Int? = null, chunkY: Int? = null, regionPlane: Int? = null) {
        val index = cache.getIndex(5)

        //Get the archive id's for the regions
        val landIndex = index.getArchiveId("l${regionX}_$regionY")
        val mapIndex = index.getArchiveId("m${regionX}_$regionY")

        //Make sure the cache has the necessary files
        if (landIndex == -1 || mapIndex == -1) {
            return
        }

        //Get the map files
        val landContainerData = index.getFile(landIndex)
        val mapContainerData = index.getFile(mapIndex)

        var settings: Array<Array<ByteArray>>? = null
        if (mapContainerData != null) {
            //Load the clipping
            //TODO settings could be cached for better performance
            settings = clipping.loadSettings(mapContainerData)
            clipping.applySettings(entityId, settings, rotation, chunkX, chunkY, regionPlane)
        }

        if (landContainerData != null) {
            //Load the objects
            objects.loadObjects(entityId, x, y, landContainerData, settings, rotation, chunkX, chunkY, regionPlane)
        } else {
            logger.warn("Missing xteas for region ${(regionX shl 8) + regionY}.")
        }
    }
}