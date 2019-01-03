package worlds.gregs.hestia.game.map

object MapConstants {
    val MAP_SIZES = intArrayOf(104, 120, 136, 168)
    const val MAP_TYPE = 0

    const val REGION_PLANES = 4
    const val REGION_SIZE = 64

    val PLANE_RANGE = 0 until REGION_PLANES
    val REGION_RANGE = 0 until REGION_SIZE

    fun isOutOfBounds(localX: Int, localY: Int): Boolean {
        return localX < 0 || localX >= REGION_SIZE || localY < 0 || localY >= REGION_SIZE
    }
}