package worlds.gregs.hestia.game.plugins.collision.systems

import com.artemis.annotations.Wire
import worlds.gregs.hestia.api.collision.ObjectCollision
import worlds.gregs.hestia.game.entity.Position
import worlds.gregs.hestia.api.map.Map
import worlds.gregs.hestia.api.region.Regions

@Wire(failOnNull = false)
class ObjectCollisionSystem : ObjectCollision() {

    private var map: Map? = null
    private var regions: Regions? = null
    private var single = false
    private var regionId = 0
    private var plane = 0

    override fun load(position: Position, single: Boolean) {
        val graphBaseX = position.x - GRAPH_SIZE / 2
        val graphBaseY = position.y - GRAPH_SIZE / 2
        val plane = position.plane
        val default = if (map == null) 0 else -1
        this.single = single
        if (single) {
            regionId = position.regionId
            this.plane = position.plane
            return
        }
        for (transmitRegionX in (graphBaseX shr 6)..(graphBaseX + (GRAPH_SIZE - 1) shr 6)) {
            for (transmitRegionY in (graphBaseY shr 6)..(graphBaseY + (GRAPH_SIZE - 1) shr 6)) {
                val startX = Math.max(graphBaseX, transmitRegionX shl 6)
                val startY = Math.max(graphBaseY, transmitRegionY shl 6)
                val endX = Math.min(graphBaseX + GRAPH_SIZE, (transmitRegionX shl 6) + 64)
                val endY = Math.min(graphBaseY + GRAPH_SIZE, (transmitRegionY shl 6) + 64)
                val regionId = transmitRegionX shl 8 or transmitRegionY
                val clipping = map?.getClipping(regions?.getEntityId(regionId))
                if (clipping != null) {
                    val masks = clipping.getMasks(plane)
                    for (fillX in startX until endX) {
                        for (fillY in startY until endY) {
                            clip[fillX - graphBaseX][fillY - graphBaseY] = masks[fillX and 0x3F][fillY and 0x3F]
                        }
                    }
                } else {
                    for (fillX in startX until endX) {
                        for (fillY in startY until endY) {
                            clip[fillX - graphBaseX][fillY - graphBaseY] = default//If map plugin isn't loaded allow no-clip, otherwise no movement
                        }
                    }
                }
            }
        }
    }

    override fun collides(localX: Int, localY: Int, mask: Int): Boolean {
        return if (single) {
            if (localX < 0 || localX >= 64 || localY < 0 || localY >= 64) {
                return false
            }
            val clipping = map?.getClipping(regions?.getEntityId(regionId))
            val clip = clipping?.getMask(localX, localY, plane) ?: return false
            clip and mask != 0
        } else {
            clip[localX][localY] and mask != 0
        }

    }

    companion object {
        private const val GRAPH_SIZE = 128
        private val clip = Array(GRAPH_SIZE) { IntArray(GRAPH_SIZE) }

        private fun print(array: Array<IntArray>) {
            for (y in array[0].indices.reversed()) {
                for (x in array.indices) {
                    print(if (array[x][y] != 0) 1 else " ")
                    print(" ")
                }
                println()
            }
        }
    }
}