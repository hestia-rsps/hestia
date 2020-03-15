package worlds.gregs.hestia.core.world.map.model.components

import com.artemis.annotations.PooledWeaver
import worlds.gregs.hestia.core.world.map.api.Collisions

@PooledWeaver
class CollisionMap : Collisions() {
    private val flags by lazy { Array(4) { Array(64) { IntArray(64) } } }

    override fun getFlags(plane: Int): Array<IntArray> {
        return flags[plane]
    }

    override fun getFlag(localX: Int, localY: Int, plane: Int): Int {
        return flags[plane][localX][localY]
    }

    override fun addFlag(localX: Int, localY: Int, plane: Int, value: Int) {
        flags[plane][localX][localY] = flags[plane][localX][localY] or value
    }

    override fun removeFlag(localX: Int, localY: Int, plane: Int, value: Int) {
        flags[plane][localX][localY] = flags[plane][localX][localY] and value.inv()
    }

    override fun setFlag(localX: Int, localY: Int, plane: Int, value: Int) {
        flags[plane][localX][localY] = value
    }
}