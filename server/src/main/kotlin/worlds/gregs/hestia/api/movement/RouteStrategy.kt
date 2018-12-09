package worlds.gregs.hestia.api.movement

import worlds.gregs.hestia.api.collision.Collision

interface RouteStrategy {

    val destinationX: Int

    val destinationY: Int

    val sizeX: Int

    val sizeY: Int

    fun exit(currentX: Int, currentY: Int, sizeX: Int, sizeY: Int, clipBaseX: Int, clipBaseY: Int, collision: Collision?): Boolean

    override fun equals(other: Any?): Boolean

    override fun hashCode(): Int

}