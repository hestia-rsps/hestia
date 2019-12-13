package worlds.gregs.hestia.core.world.map.api.container

import com.artemis.Aspect
import com.artemis.utils.IntBag
import net.mostlyoriginal.api.utils.QuadTree
import worlds.gregs.hestia.core.world.map.api.QuadMap
import worlds.gregs.hestia.core.world.map.model.MapConstants.REGION_PLANES

abstract class EntityMap(builder: Aspect.Builder) : QuadMap(builder) {

    private val tries = arrayOfNulls<QuadTree?>(REGION_PLANES)
    private val positions = HashMap<Int, ArrayList<Int>>()

    override fun removeFromTile(entityId: Int, hash: Int) {
        positions[hash]?.remove(entityId)
    }

    override fun setTile(entityId: Int, hash: Int) {
        positions.getOrPut(hash) { ArrayList() }.add(entityId)
    }

    override fun getTile(hash: Int): ArrayList<Int>? {
        return positions[hash]
    }

    override fun insert(entityId: Int, x: Int, y: Int, plane: Int, width: Int, height: Int) {
        getTree(plane).insert(entityId, x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat())
    }

    override fun update(entityId: Int, x: Int, y: Int, plane: Int, width: Int, height: Int) {
        getTree(plane).update(entityId, x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat())
    }

    override fun remove(entityId: Int, plane: Int) {
        getTree(plane).remove(entityId)
    }

    override fun getExact(fill: IntBag, x: Int, y: Int, plane: Int): IntBag {
        return getTree(plane).getExact(fill, x.toFloat(), y.toFloat(), 1f, 1f)
    }

    override fun getExact(fill: IntBag, x: Int, y: Int, plane: Int, width: Int, height: Int): IntBag {
        return getTree(plane).getExact(fill, x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat())
    }

    override fun get(fill: IntBag, x: Int, y: Int, plane: Int, width: Int, height: Int): IntBag {
        return getTree(plane).get(fill, x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat())
    }

    /**
     * Get's a quad-tree for the [plane], inserting if non-existent
     * @param plane The plane to get the tree for
     * @return The single plane QuadTree
     */
    private fun getTree(plane: Int): QuadTree {
        return tries[plane] ?: let {
            val tree = QuadTree(0f, 0f, MAP_SIZE, MAP_SIZE)
            tries[plane] = tree
            tree
        }
    }

    override fun dispose() {
        tries.forEach {
            it?.dispose()
        }
    }
}