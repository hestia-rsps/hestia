package world.gregs.hestia.game.map

import world.gregs.hestia.game.map.MapConstants.MAP_SIZE

data class MapIndex(val regionId: Int, val mapIndex: Int, val landIndex: Int) {

    val x: Int
        get() = (regionId shr 8 and 0xff) * MAP_SIZE

    val y: Int
        get() = (regionId and 0xff) * MAP_SIZE

    companion object {
        var indices: Map<Int, MapIndex>? = null

        fun init(indices: Map<Int, MapIndex>) {
            Companion.indices = indices
        }
    }
}