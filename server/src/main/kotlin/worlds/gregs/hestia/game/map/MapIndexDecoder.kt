package worlds.gregs.hestia.game.map

import world.gregs.hestia.core.services.Cache
import java.io.IOException

class MapIndexDecoder : Runnable {

    fun decode(): Map<Int, MapIndex> {
        val index = Cache.getIndex(5)!!
        val definitions = HashMap<Int, MapIndex>()
        val range = 0..162
        for (x in range) {
            for (y in range) {
                val mapIndex = index.getArchiveId("m${x}_$y")
                val landIndex = index.getArchiveId("l${x}_$y")
                if (mapIndex != -1 && landIndex != -1) {
                    val regionId = (x shl 8) + y
                    definitions[regionId] = MapIndex(regionId, mapIndex, landIndex)
                }
            }
        }
        return definitions
    }

    override fun run() {
        try {
            MapIndex.init(decode())
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Cache.init()
            MapIndexDecoder().decode()
        }
    }
}