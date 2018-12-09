package worlds.gregs.hestia.game.plugins.map.components

import com.artemis.annotations.PooledWeaver
import worlds.gregs.hestia.api.map.Clipping

@PooledWeaver
abstract class ClippingImpl : Clipping() {
    private val masks by lazy { Array(4) { Array(64) { IntArray(64) } } }

    override fun getMasks(plane: Int): Array<IntArray> {
        return masks[plane]
    }

    override fun getMask(localX: Int, localY: Int, plane: Int): Int {
        return masks[plane][localX][localY]
    }

    override fun addMask(localX: Int, localY: Int, plane: Int, value: Int) {
        masks[plane][localX][localY] = masks[plane][localX][localY] or value
    }

    override fun removeMask(localX: Int, localY: Int, plane: Int, value: Int) {
        masks[plane][localX][localY] = masks[plane][localX][localY] and value.inv()
    }

    override fun setMask(localX: Int, localY: Int, plane: Int, value: Int) {
        masks[plane][localX][localY] = value
    }
}