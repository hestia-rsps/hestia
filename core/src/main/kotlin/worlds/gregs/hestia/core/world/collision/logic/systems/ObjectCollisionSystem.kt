package worlds.gregs.hestia.core.world.collision.logic.systems

import com.artemis.annotations.Wire
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.world.collision.api.ObjectCollision
import worlds.gregs.hestia.core.world.map.api.Map
import worlds.gregs.hestia.core.world.region.api.Regions

@Wire(failOnNull = false)
class ObjectCollisionSystem : ObjectCollision() {

    private var map: Map? = null
    private var regions: Regions? = null
    private var plane = 0

    override fun load(position: Position) {
        plane = position.plane
    }

    override fun collides(localX: Int, localY: Int, flag: Int): Boolean {
        val collisions = map?.getCollision(regions?.getEntityId(Position.regionId(localX, localY)))
        val clip = collisions?.getFlag(localX % 64, localY % 64, plane) ?: return false
        return clip and flag != 0
    }

    companion object {

        private fun print(array: Array<IntArray>) {
            for (y in array[0].indices.reversed()) {
                for (x in array.indices) {
                    if(x in 66..68 && y in 65..67) {
                        print(array[x][y])
                    } else {
                        print(if (array[x][y] != 0) 1 else " ")
                    }
                    print(" ")
                }
                println()
            }
        }
    }
}