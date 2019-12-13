package worlds.gregs.hestia.core.world.map.model

object MapConstants {
    val MAP_SIZES = intArrayOf(104, 120, 136, 168)
    const val MAP_TYPE = 0

    const val REGION_PLANES = 4

    const val CHUNK_COUNT = 8//Number of chunks in a region
    const val REGION_COUNT = 255//Number of regions in the map

    const val CHUNK_SIZE = 8//Number of tiles in a chunk
    const val REGION_SIZE = 64//Number of tiles in a chunk

    val PLANE_RANGE = 0 until REGION_PLANES
    val REGION_RANGE = 0 until REGION_SIZE

    fun isOutOfBounds(localX: Int, localY: Int): Boolean {
        return localX < 0 || localX >= REGION_SIZE || localY < 0 || localY >= REGION_SIZE
    }
}