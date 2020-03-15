package worlds.gregs.hestia.core.world.movement.api

import worlds.gregs.hestia.core.world.collision.api.Collision


interface RouteStrategy {

    val destinationX: Int

    val destinationY: Int

    val sizeX: Int

    val sizeY: Int

    fun exit(currentX: Int, currentY: Int, sizeX: Int, sizeY: Int, collision: Collision?): Boolean

    override fun equals(other: Any?): Boolean

    override fun hashCode(): Int

}