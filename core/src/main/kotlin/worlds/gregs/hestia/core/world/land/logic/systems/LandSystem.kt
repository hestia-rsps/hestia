package worlds.gregs.hestia.core.world.land.logic.systems

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.world.land.api.Land
import worlds.gregs.hestia.core.world.land.model.components.LandObjects
import worlds.gregs.hestia.core.world.region.api.Regions

/**
 * LandSystem
 * Handles list of region objects
 */
@Wire(failOnNull = false)
class LandSystem : Land() {

    private lateinit var landObjectsMapper: ComponentMapper<LandObjects>
    private var regions: Regions? = null

    override fun addObject(entityId: Int, localX: Int, localY: Int, plane: Int, regionId: Int) {
        land(regionId, localX, localY, plane) { objects, position ->

            if (!objects.list.containsKey(position) || objects.list[position] == null) {
                objects.list[position] = ArrayList()
            }

            val tileObjects = objects.list[position]!!
            tileObjects.add(entityId)
        }
    }

    override fun removeObject(entityId: Int, localX: Int, localY: Int, plane: Int, regionId: Int) {
        land(regionId, localX, localY, plane) { objects, position ->

            val tileObjects = objects.list[position] ?: return@land
            tileObjects.remove(entityId)
        }
    }

    override fun unload(entityId: Int) {
        val objects = landObjectsMapper.get(entityId)
        objects.list.clear()
    }

    private fun land(regionId: Int, localX: Int, localY: Int, plane: Int, action: (LandObjects, Int) -> Unit) {
        val regionEntity = regions?.getEntityId(regionId) ?: return
        val objects = landObjectsMapper.get(regionEntity)
        val position = Position.hash18Bit(localX, localY, plane)
        action(objects, position)
    }
}