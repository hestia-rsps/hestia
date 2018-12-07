package worlds.gregs.hestia.game.path

interface RouteStrategy {

    val destinationX: Int

    val destinationY: Int

    val sizeX: Int

    val sizeY: Int

    fun exit(currentX: Int, currentY: Int, sizeX: Int, sizeY: Int, clip: Array<IntArray>, clipBaseX: Int, clipBaseY: Int): Boolean

    override fun equals(other: Any?): Boolean

    override fun hashCode(): Int

}